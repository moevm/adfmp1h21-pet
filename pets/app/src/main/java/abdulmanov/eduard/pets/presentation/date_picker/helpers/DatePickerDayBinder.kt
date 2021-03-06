package abdulmanov.eduard.pets.presentation.date_picker.helpers

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.ItemDatePickerCalendarDayBinding
import abdulmanov.eduard.pets.presentation._common.extensions.setTextColorRes
import android.view.View
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate

class DatePickerDayBinder(
    private val size: Int,
    private val clickListener: (LocalDate) -> Unit
) : DayBinder<DatePickerDayBinder.DayViewContainer> {

    var selectedDate: LocalDate? = null

    override fun create(view: View): DayViewContainer {
        return DayViewContainer(view)
    }

    override fun bind(container: DayViewContainer, day: CalendarDay) {
        container.day = day
        container.textView.text = day.date.dayOfMonth.toString()

        when {
            day.owner != DayOwner.THIS_MONTH -> {
                container.textView.background = null
                container.textView.setTextColorRes(R.color.colorPrimaryLight)
            }
            day.date == selectedDate -> {
                container.textView.setBackgroundResource(R.drawable.bg_selected_date_picker_day)
                container.textView.setTextColorRes(R.color.colorPrimary)
            }
            day.date == LocalDate.now() -> {
                container.textView.setBackgroundResource(R.drawable.bg_current_date_picker_day)
                container.textView.setTextColorRes(android.R.color.white)
            }
            else -> {
                container.textView.background = null
                container.textView.setTextColorRes(android.R.color.white)
            }
        }
    }

    inner class DayViewContainer(view: View): ViewContainer(view) {

        val textView = (ItemDatePickerCalendarDayBinding.bind(view).oneDayFrameLayout.getChildAt(0) as TextView)

        lateinit var day: CalendarDay

        init {
            textView.updateLayoutParams {
                height = size
                width = size
            }

            view.setOnClickListener {
                if(day.owner == DayOwner.THIS_MONTH) {
                    clickListener.invoke(day.date)
                }
            }
        }
    }
}