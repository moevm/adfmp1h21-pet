package abdulmanov.eduard.pets.presentation.event

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentEventBinding
import abdulmanov.eduard.pets.domain.models.event.RepeatMode
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.pets.presentation._common.extensions.initSpinner
import abdulmanov.eduard.pets.presentation.date_picker.DatePickerBottomSheetDialog
import abdulmanov.eduard.pets.presentation.time_picker.TimePickerBottomSheetDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener

class EventFragment : BaseFragment<FragmentEventBinding>(),
    TimePickerBottomSheetDialog.TimePickerCallback,
    DatePickerBottomSheetDialog.DatePickerCallback {

    private val viewModel by initViewModel<EventViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onChangeDate(date: String, viewId: Int) {
        when(viewId){
            R.id.dateTextInputEditText -> binding.dateTextInputEditText.setText(date)
        }
    }

    override fun onChangeTime(time: String, viewId: Int) {
        when(viewId){
            R.id.timeTextInputEditText -> binding.timeTextInputEditText.setText(time)
        }
    }

    private fun initUI(){
        binding.toolbar.run {
            setTitle(R.string.event_new_title)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
        }

        val repeatModeItems = requireContext().resources.getStringArray(R.array.repeat_mode).toList()
        binding.repeatModeTextView.initSpinner(repeatModeItems)
        binding.repeatModeTextView.addTextChangedListener {
            val index = repeatModeItems.indexOf(it.toString())
            val repeatMode = RepeatMode.values()[index]
            binding.dateTextInputLayout.isVisible = repeatMode.isNeedDate
        }

        binding.dateTextInputEditText.setOnClickListener {
            openDatePicker()
        }

        binding.timeTextInputEditText.setOnClickListener {
            openTimePicker()
        }

        binding.notificationSwitch.setOnCheckedChangeListener { _, checked ->
            binding.timeTextInputLayout.isVisible = checked
        }
    }

    private fun openDatePicker(){
        val date = binding.dateTextInputEditText.text.toString()
        val viewId = binding.dateTextInputEditText.id
        val dialog = DatePickerBottomSheetDialog.newInstance(date, viewId)
        dialog.show(childFragmentManager, DatePickerBottomSheetDialog.TAG)
    }

    private fun openTimePicker(){
        val time = binding.timeTextInputEditText.text.toString()
        val viewId = binding.timeTextInputEditText.id
        val dialog = TimePickerBottomSheetDialog.newInstance(time, viewId)
        dialog.show(childFragmentManager, TimePickerBottomSheetDialog.TAG)
    }

    companion object {
        private const val ARG_ID_EVENT = "id_event"

        fun newInstance(eventId: Int): EventFragment {
            return EventFragment().apply {
                arguments = bundleOf(ARG_ID_EVENT to eventId)
            }
        }
    }
}