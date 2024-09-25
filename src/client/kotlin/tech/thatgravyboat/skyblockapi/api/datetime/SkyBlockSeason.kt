package tech.thatgravyboat.skyblockapi.api.datetime

enum class SkyBlockSeason {
    EARLY_SPRING,
    SPRING,
    LATE_SPRING,

    EARLY_SUMMER,
    SUMMER,
    LATE_SUMMER,

    EARLY_AUTUMN,
    AUTUMN,
    LATE_AUTUMN,

    EARLY_WINTER,
    WINTER,
    LATE_WINTER,
    ;

    companion object {

        fun parse(value: String): SkyBlockSeason? = runCatching {
            valueOf(value.replace(" ", "_").uppercase())
        }.getOrNull()
    }
}