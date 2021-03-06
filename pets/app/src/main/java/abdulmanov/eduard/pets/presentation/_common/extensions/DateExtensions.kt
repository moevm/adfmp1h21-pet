package abdulmanov.eduard.pets.presentation._common.extensions

import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

fun getDaysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()

    if(firstDayOfWeek != DayOfWeek.MONDAY){
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)

        daysOfWeek = rhs + lhs
    }

    return daysOfWeek
}

fun getMonthsForCalendar(): Pair<YearMonth, YearMonth> {
    val currentMonth = YearMonth.now()
    return Pair(currentMonth.minusMonths(24), currentMonth.plusMonths(24))
}