package tech.thatgravyboat.skyblockapi.api.events.info

import tech.thatgravyboat.skyblockapi.api.events.base.SkyblockEvent

data class TabWidgetChangeEvent(
    val widget: TabWidget,
    val old: List<String>,
    val new: List<String>,
) : SkyblockEvent()

enum class TabWidget {
    AREA,
    PROFILE,
    PET,
    DAILY_QUESTS,
    FORGES,
    COMMISSIONS,
    SKILLS,
    POWDERS,
    ELECTION,
    CRYSTALS,
    BESTIARY,
    COLLECTION,
    STATS,
    EVENT,
    PARTY,
    MINIONS,
    DUNGEONS,
    ESSENCE,
    GOOD_TO_KNOW,
    SHEN,
}