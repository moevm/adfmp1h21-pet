package abdulmanov.eduard.pets.domain.models.event

enum class RepeatMode(val value: Int, val isNeedDate: Boolean) {
    DO_NOT_REPEAT(0, true),
    REPEAT_EVERY_DAY(1, false),
    REPEAT_EVERY_WEEK(2, true),
    REPEAT_EVERY_MONTH(3, true)
}