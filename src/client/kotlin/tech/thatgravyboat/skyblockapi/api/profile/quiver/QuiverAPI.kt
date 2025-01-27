package tech.thatgravyboat.skyblockapi.api.profile.quiver

import net.minecraft.world.item.ItemStack
import tech.thatgravyboat.skyblockapi.api.data.SkyBlockCategory
import tech.thatgravyboat.skyblockapi.api.data.stored.QuiverStorage
import tech.thatgravyboat.skyblockapi.api.datatype.DataTypes
import tech.thatgravyboat.skyblockapi.api.datatype.getData
import tech.thatgravyboat.skyblockapi.api.events.base.Subscription
import tech.thatgravyboat.skyblockapi.api.events.screen.ContainerChangeEvent
import tech.thatgravyboat.skyblockapi.api.events.screen.ContainerInitializedEvent
import tech.thatgravyboat.skyblockapi.api.events.screen.PlayerHotbarChangeEvent
import tech.thatgravyboat.skyblockapi.api.remote.SkyBlockItems
import tech.thatgravyboat.skyblockapi.modules.Module
import tech.thatgravyboat.skyblockapi.utils.extentions.addOrPut
import tech.thatgravyboat.skyblockapi.utils.extentions.getRawLore
import tech.thatgravyboat.skyblockapi.utils.extentions.toIntValue
import tech.thatgravyboat.skyblockapi.utils.regex.RegexGroup
import tech.thatgravyboat.skyblockapi.utils.regex.RegexUtils.anyFound
import tech.thatgravyboat.skyblockapi.utils.regex.RegexUtils.contains

@Module
object QuiverAPI {

    private val inventoryGroup = RegexGroup.INVENTORY.group("quiver")

    private val activeArrowRegex = inventoryGroup.create(
        "active",
        "^Active Arrow: (?<type>.+) \\((?i)(?<amount>[\\d,.kmb]+)\\)$"
    )
    private val quiverInventoryRegex = inventoryGroup.create(
        "inventory",
        "^Quiver$"
    )

    var currentArrow: String?
        get() = QuiverStorage.currentArrow
        private set(value) {
            QuiverStorage.updateCurrent(value ?: return)
        }

    var currentAmount: Int?
        get() = arrows[currentArrow]
        private set(value) {
            QuiverStorage.updateArrow(currentArrow ?: return, value ?: return)
        }

    val arrows: Map<String, Int>
        get() = mutableArrows

    private inline val mutableArrows: MutableMap<String, Int>
        get() = QuiverStorage.arrows

    @Subscription
    fun onHotbarChange(event: PlayerHotbarChangeEvent) {
        if (event.slot != 8) return
        val item = event.item
        if (item.getData(DataTypes.QUIVER_ARROW) != true) return
        activeArrowRegex.anyFound(item.getRawLore(), "type", "amount") { (type, amount) ->
            val id = SkyBlockItems.getIdByDisplayName(type)
            currentArrow = id
            if (id == null) return@anyFound
            currentAmount = amount.toIntValue()
        }
    }

    @Subscription
    fun onInventoryInitialized(event: ContainerInitializedEvent) {
        handleQuiverInventory(event.title, event.itemStacks)
    }

    @Subscription
    fun onInventoryChange(event: ContainerChangeEvent) {
        handleQuiverInventory(event.title, event.inventory)
    }

    private fun handleQuiverInventory(title: String, items: List<ItemStack>) {
        if (!quiverInventoryRegex.contains(title)) return

        val newArrows = buildMap {
            for (item in items) {
                val category = item.getData(DataTypes.CATEGORY)
                if (category != SkyBlockCategory.ARROW) continue
                val id = item.getData(DataTypes.ID) ?: continue
                addOrPut(id, item.count)
            }
        }
        QuiverStorage.setArrows(newArrows)
    }

}
