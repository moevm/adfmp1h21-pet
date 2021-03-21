package abdulmanov.eduard.pets.presentation.event

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentEventBinding
import abdulmanov.eduard.pets.domain.models.event.RepeatMode
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.pets.presentation._common.extensions.bind
import abdulmanov.eduard.pets.presentation._common.extensions.initSpinner
import abdulmanov.eduard.pets.presentation.event.dialogs.date_picker.DatePickerBottomSheetDialog
import abdulmanov.eduard.pets.presentation.event.dialogs.time_picker.TimePickerBottomSheetDialog
import abdulmanov.eduard.pets.presentation.event.model.EventPresentationModel
import abdulmanov.eduard.pets.presentation.notify.NotifyWork
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit

class EventFragment : BaseFragment<FragmentEventBinding>(),
    TimePickerBottomSheetDialog.TimePickerCallback,
    DatePickerBottomSheetDialog.DatePickerCallback {

    private val viewModel by initViewModel<EventViewModel>()

    private val eventId: Int by lazy { requireArguments().getInt(ARG_ID_EVENT) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showApplyProgress.observe(viewLifecycleOwner, Observer(binding.applyProgressButton::showProgress))
        viewModel.initializationFieldsEvent.observe(viewLifecycleOwner, { initFields() })
        viewModel.createNotificationEvent.observe(viewLifecycleOwner, Observer(::createNotification))

        if (savedInstanceState == null) {
            viewModel.setEventOrDefault(eventId)
        }
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
            setTitle(if (eventId == -1) R.string.event_new_title else R.string.event_edit_title)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
        }

        binding.repeatModeTextView.initSpinner(requireContext().resources.getStringArray(R.array.repeat_mode).toList())

        binding.nameTextInputEditText.bind { viewModel.event?.name = it }
        binding.dateTextInputEditText.bind { viewModel.event?.date = it }
        binding.timeTextInputEditText.bind { viewModel.event?.time = it }
        binding.repeatModeTextView.bind({ viewModel.event?.repeatMode = it as RepeatMode }, { RepeatMode.values()[it] })
        binding.notificationSwitch.bind {
            viewModel.event?.isNotification = it
            binding.timeTextInputLayout.isVisible = it
        }

        binding.dateTextInputEditText.setOnClickListener {
            openDatePicker()
        }

        binding.timeTextInputEditText.setOnClickListener {
            openTimePicker()
        }

        binding.applyProgressButton.setText(if (eventId == -1) R.string.event_button_create else R.string.event_button_save)
        binding.applyProgressButton.setOnClickListener {
            viewModel.createOrUpdateEvent()
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

    private fun initFields() {
        val event = viewModel.event!!

        binding.nameTextInputEditText.setText(event.name)
        binding.dateTextInputEditText.setText(event.date)
        binding.notificationSwitch.isChecked = event.isNotification
        binding.timeTextInputEditText.setText(event.time)

        val repeatMode = binding.repeatModeTextView.adapter.getItem(event.repeatMode.value) as String
        binding.repeatModeTextView.setText(repeatMode,false)
    }

    private fun createNotification(event: EventPresentationModel){
        val data = NotifyWork.newData(event.id)

        val currentCalendar = Calendar.getInstance()
        val notifyCalendar = EventPresentationModel.getCalendar(event)

        if (notifyCalendar.before(currentCalendar)) {
            notifyCalendar.add(Calendar.HOUR_OF_DAY, 24)
        }

        val delay = notifyCalendar.timeInMillis - currentCalendar.timeInMillis

        val dailyWorkRequest = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInputData(data)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag(event.id.toString())
            .build()

        WorkManager.getInstance(requireContext())
            .beginUniqueWork(event.id.toString(), ExistingWorkPolicy.REPLACE, dailyWorkRequest)
            .enqueue()

        viewModel.onBackCommandClick()
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