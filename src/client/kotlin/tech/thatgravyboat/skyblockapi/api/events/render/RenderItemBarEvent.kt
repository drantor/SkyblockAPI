package tech.thatgravyboat.skyblockapi.api.events.render

import net.minecraft.world.item.ItemStack
import tech.thatgravyboat.skyblockapi.api.events.base.SkyblockEvent

class RenderItemBarEvent(
    val item: ItemStack,
    var color: Int,
    var percent: Float
) : SkyblockEvent()