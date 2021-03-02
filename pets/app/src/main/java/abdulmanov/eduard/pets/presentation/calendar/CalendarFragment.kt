package abdulmanov.eduard.pets.presentation.calendar

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentCalendarBinding
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View

class CalendarFragment: BaseFragment<FragmentCalendarBinding>() {

    private val viewModel by initViewModel<CalendarViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuItem -> viewModel.openScreenOptions()
        }
        return true
    }

    private fun initUI(){
        binding.toolbar.run {
            inflateMenu(R.menu.menu_calendar)
            setOnMenuItemClickListener(this@CalendarFragment::onOptionsItemSelected)
        }
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }
}