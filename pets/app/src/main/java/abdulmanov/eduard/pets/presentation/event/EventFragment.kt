package abdulmanov.eduard.pets.presentation.event

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentCalendarBinding
import abdulmanov.eduard.pets.databinding.FragmentEventBinding
import abdulmanov.eduard.pets.domain.models.BirthDate
import abdulmanov.eduard.pets.domain.models.Sex
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.bind
import abdulmanov.eduard.pets.presentation.main.MainActivity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import java.util.*

class EventFragment: BaseFragment<FragmentEventBinding>() {
    companion object {
        fun newInstance() = EventFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        initSpinner(binding.typeTextView, requireContext().resources.getStringArray(R.array.repeat_type).toList())
    }

    private fun initSpinner(view: AutoCompleteTextView, items: List<String>){
        view.setAdapter(ArrayAdapter(requireContext(), R.layout.item_spinner, items))

        binding.dateTextView.setOnFocusChangeListener {
                _, hasFocus -> if (hasFocus) showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = object : DatePickerDialog(
            requireContext(),
            OnDateSetListener { _, _, _, _ -> },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ) {
            override fun onDateChanged(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                super.onDateChanged(view, year, month, dayOfMonth)
                val currDay = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                val currMonth = if (month < 10) "0$month" else month.toString()
                binding.dateTextView.setText("$currDay.$currMonth.$year")
                dismiss()
            }
        }

        datePicker.show()
    }
}