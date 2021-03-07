package abdulmanov.eduard.pets.presentation.event.date_picker.helpers

import abdulmanov.eduard.pets.databinding.ItemDatePickerCalendarDayLegendBinding
import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class DatePickerMonthHeaderBinder(
    private val daysOfWeek: Array<DayOfWeek>
) : MonthHeaderFooterBinder<DatePickerMonthHeaderBinder.MonthViewContainer> {

    override fun create(view: View) = MonthViewContainer(view)

    @SuppressLint("SetTextI18n")
    override fun bind(container: MonthViewContainer, month: CalendarMonth) {
        val monthStr = month.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val formattedMonth = monthStr[0].toUpperCase() + monthStr.slice(1 until monthStr.length)
        container.monthTextView.text = "$formattedMonth ${month.year}"

        if(container.legendLinearLayout.tag == null){
            container.legendLinearLayout.tag = month.yearMonth
            container.legendLinearLayout.children
                .map { it as TextView }
                .forEachIndexed { index, textView ->
                    textView.text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.getDefault())
                }
        }
    }

    class MonthViewContainer(view: View): ViewContainer(view){
        val rootView = ItemDatePickerCalendarDayLegendBinding.bind(view).root
        val monthTextView = rootView.getChildAt(0) as TextView
        val legendLinearLayout = rootView.getChildAt(1) as LinearLayout
    }
}