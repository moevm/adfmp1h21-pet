package abdulmanov.eduard.pets.presentation.calendar.helpers

import abdulmanov.eduard.pets.databinding.ItemTimetableCalendarDayLegendBinding
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class CalendarMonthHeaderBinder(
    private val daysOfWeek: Array<DayOfWeek>
) : MonthHeaderFooterBinder<CalendarMonthHeaderBinder.MonthViewContainer> {

    override fun create(view: View) = MonthViewContainer(view)

    override fun bind(container: MonthViewContainer, month: CalendarMonth) {
        if(container.legendLinearLayout.tag == null){
            container.legendLinearLayout.tag = month.yearMonth
            container.legendLinearLayout.children
                .map { it as TextView }
                .forEachIndexed { index, textView ->
                    val dayOfWeek = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    textView.text = dayOfWeek.toUpperCase(Locale.ROOT)
                }
        }
    }

    class MonthViewContainer(view: View): ViewContainer(view){
        val legendLinearLayout = ItemTimetableCalendarDayLegendBinding.bind(view).legendLinearLayout
    }
}