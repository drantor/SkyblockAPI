package tech.thatgravyboat.skyblockapi.api.events.location

import net.hypixel.data.type.ServerType
import tech.thatgravyboat.skyblockapi.api.events.base.SkyblockEvent

data class ServerChangeEvent(
    val name: String,
    val type: ServerType?,
    val lobby: String?,
    val mode: String?,
    val map: String?,
) : SkyblockEvent()