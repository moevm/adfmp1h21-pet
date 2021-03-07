package abdulmanov.eduard.pets.presentation.calendar.dialogs.edit_event

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.BottomDialogEditEventBinding
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseBottomSheetDialogFragment
import abdulmanov.eduard.pets.presentation.event.model.EventPresentationModel
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment

class EditEventBottomSheetDialog: BaseBottomSheetDialogFragment<BottomDialogEditEventBinding>() {

    private val viewModel by initViewModel<EditEventViewModel>()

    private lateinit var callback: EditEventUpdate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        callback = parentFragment as EditEventUpdate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.deleteEvent.observe(viewLifecycleOwner, { callCallbackAndDismiss() })
    }

    private fun initUI(){
        val event = requireArguments().getParcelable<EventPresentationModel>(ARG_EVENT)!!

        binding.titleTextView.text = event.name

        binding.editEventContainer.setOnClickListener {
            dismiss()
            viewModel.openScreenEditEvent(event)
        }

        binding.deleteEventContainer.setOnClickListener {
            viewModel.delete(event)
        }
    }

    private fun callCallbackAndDismiss(){
        callback.onUpdate()
        dismiss()
    }

    companion object {
        const val TAG = "EditEventBottomSheetDialog"

        private const val ARG_EVENT = "event"

        fun newInstance(event: EventPresentationModel): EditEventBottomSheetDialog {
            return EditEventBottomSheetDialog().apply {
                arguments = bundleOf(ARG_EVENT to event)
            }
        }
    }

    interface EditEventUpdate {
        fun onUpdate()
    }
}