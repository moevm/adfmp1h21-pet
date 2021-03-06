package abdulmanov.eduard.pets.domain.models.event

import java.lang.IllegalStateException

enum class RepeatMode(val value: Int) {
    DO_NOT_REPEAT(0),
    REPEAT_EVERY_DAY(1),
    REPEAT_EVERY_WEEK(2),
    REPEAT_EVERY_MONTH(3);

    companion object {

        fun getRepeatModeFromValue(value: Int): RepeatMode {
            return when (value) {
                DO_NOT_REPEAT.value -> DO_NOT_REPEAT
                REPEAT_EVERY_DAY.value -> REPEAT_EVERY_DAY
                REPEAT_EVERY_WEEK.value -> REPEAT_EVERY_WEEK
                REPEAT_EVERY_MONTH.value -> REPEAT_EVERY_MONTH
                else -> throw IllegalStateException("RepeatMode not found")
            }
        }
    }
}