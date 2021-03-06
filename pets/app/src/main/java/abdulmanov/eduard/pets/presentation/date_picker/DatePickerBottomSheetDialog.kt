package abdulmanov.eduard.pets.presentation.date_picker

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.BottomDialogDatePickerBinding
import abdulmanov.eduard.pets.presentation._common.base.BaseBottomSheetDialogFragment
import abdulmanov.eduard.pets.presentation._common.extensions.dpToPx
import abdulmanov.eduard.pets.presentation._common.extensions.getDaysOfWeekFromLocale
import abdulmanov.eduard.pets.presentation._common.extensions.getMonthsForCalendar
import abdulmanov.eduard.pets.presentation._common.extensions.getScreenSize
import abdulmanov.eduard.pets.presentation.date_picker.helpers.DatePickerDayBinder
import abdulmanov.eduard.pets.presentation.date_picker.helpers.DatePickerMonthHeaderBinder
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.kizitonwose.calendarview.utils.Size
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DatePickerBottomSheetDialog: BaseBottomSheetDialogFragment<BottomDialogDatePickerBinding>() {

    private var callback: DatePickerCallback? = null

    private val selectedDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = parentFragment as DatePickerCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.run {
            val screenSize = context.getScreenSize()
            daySize = Size(((screenSize.x -64.dpToPx())/7).toInt(),screenSize.x/10)

            val daysOfWeek = getDaysOfWeekFromLocale()
            val monthForCalendar = getMonthsForCalendar()

            setup(monthForCalendar.first, monthForCalendar.second, daysOfWeek.first())
            dayBinder = DatePickerDayBinder(daySize.height, ::selectDate)
            monthHeaderBinder = DatePickerMonthHeaderBinder(daysOfWeek)

            val currentDate = if(savedInstanceState == null) {
                requireArguments().getString(ARG_CURRENT_SELECTED_DATE, null)
            }else{
                savedInstanceState.getString(ARG_CURRENT_SELECTED_DATE, null)
            }
            scrollToDate(DatePickerDelegate.getDateAsLocalDate(currentDate))
            selectDate(DatePickerDelegate.getDateAsLocalDate(currentDate))
        }

        binding.throwOffTextView.setOnClickListener {
            selectDate(null)
            callback?.onChangeDate(
                DatePickerDelegate.getDateAsString(null),
                requireArguments().getInt(ARG_VIEW_ID)
            )
            dismiss()
        }

        binding.applyTextView.setOnClickListener {
            val dayBinder = binding.calendarView.dayBinder as DatePickerDayBinder
            callback?.onChangeDate(
                DatePickerDelegate.getDateAsString(dayBinder.selectedDate),
                requireArguments().getInt(ARG_VIEW_ID)
            )
            dismiss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val dayBinder = binding.calendarView.dayBinder as DatePickerDayBinder
        outState.putString(ARG_CURRENT_SELECTED_DATE, DatePickerDelegate.getDateAsString(dayBinder.selectedDate))
    }

    private fun selectDate(date: LocalDate?){
        val dayBinder = binding.calendarView.dayBinder as DatePickerDayBinder

        if(date != dayBinder.selectedDate){
            val oldDate = dayBinder.selectedDate
            dayBinder.selectedDate = date
            oldDate?.let { binding.calendarView.notifyDateChanged(oldDate) }
            date?.let { binding.calendarView.notifyDateChanged(date) }

            if(date != null) {
                binding.selectedDateTextView.text = selectedDateFormatter.format(date)
            }else{
                binding.selectedDateTextView.setText(R.string.date_picker_select_date)
            }
        }
    }

    companion object{
        const val TAG = "DatePickerBottomSheetDialog"

        private const val ARG_CURRENT_SELECTED_DATE = "CURRENT_SELECTED_DATE"
        private const val ARG_VIEW_ID = "VIEW_ID"

        fun newInstance(date: String?, viewId: Int): DatePickerBottomSheetDialog {
            return DatePickerBottomSheetDialog().apply {
                arguments = bundleOf(ARG_CURRENT_SELECTED_DATE to date, ARG_VIEW_ID to viewId)
            }
        }
    }

    interface DatePickerCallback {
        fun onChangeDate(date: String, viewId: Int)
    }
}