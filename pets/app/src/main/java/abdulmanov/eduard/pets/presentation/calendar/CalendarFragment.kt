package abdulmanov.eduard.pets.presentation.calendar

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentCalendarBinding
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.getDaysOfWeekFromLocale
import abdulmanov.eduard.pets.presentation._common.extensions.getMonthsForCalendar
import abdulmanov.eduard.pets.presentation._common.extensions.getScreenSize
import abdulmanov.eduard.pets.presentation.calendar.helpers.CalendarDayBinder
import abdulmanov.eduard.pets.presentation.calendar.helpers.CalendarMonthHeaderBinder
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.kizitonwose.calendarview.utils.Size
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {

    private val viewModel by initViewModel<CalendarViewModel>()

    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.currentPet.observe(viewLifecycleOwner, Observer(::setCurrentPet))

        if(savedInstanceState == null){
            viewModel.getCurrentPet()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItem -> viewModel.openScreenOptions()
        }
        return true
    }

    private fun initUI() {
        binding.toolbar.run {
            inflateMenu(R.menu.menu_calendar)
            setOnMenuItemClickListener(this@CalendarFragment::onOptionsItemSelected)
        }

        binding.calendarView.run {
            val daysOfWeek = getDaysOfWeekFromLocale()
            val monthsForCalendar = getMonthsForCalendar()

            daySize = context.getScreenSize().run { Size(x/7, x/10) }
            dayBinder = CalendarDayBinder(daySize.height, ::selectDate)
            monthHeaderBinder = CalendarMonthHeaderBinder(daysOfWeek)
            setup(monthsForCalendar.first, monthsForCalendar.second, daysOfWeek.first())
            selectDate(LocalDate.now())
            scrollToDate(LocalDate.now())

            post { monthScrollListener = { selectDate(it.yearMonth.atDay(1)) } }
        }

        binding.floatingButton.setOnClickListener {
            Log.d("FuckFuck", "onClick")
        }
    }

    private fun selectDate(date: LocalDate) {
        val dayBinder = binding.calendarView.dayBinder as CalendarDayBinder

        if(date != dayBinder.selectedDate) {
            val oldDate = dayBinder.selectedDate
            dayBinder.selectedDate = date
            binding.calendarView.notifyDateChanged(oldDate)
            binding.calendarView.notifyDateChanged(date)
            binding.currentSelectDateTextView.text = selectionFormatter.format(date)
        }
    }

    private fun setCurrentPet(pet: PetPresentationModel){
        if(pet.avatar.isNotEmpty()){
            val uri = Uri.parse(pet.avatar)
            binding.avatarImageView.setImageURI(uri)
        }
        binding.titleTextView.text = pet.name
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }
}