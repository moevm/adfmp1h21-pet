package abdulmanov.eduard.pets.presentation.event.time_picker

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.BottomDialogTimePickerBinding
import abdulmanov.eduard.pets.presentation._common.base.BaseBottomSheetDialogFragment
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment

class TimePickerBottomSheetDialog: BaseBottomSheetDialogFragment<BottomDialogTimePickerBinding>() {

    private var callback: TimePickerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = parentFragment as? TimePickerCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timePicker.run {
            setIs24HourView(true)

            val currentTime = if(savedInstanceState == null){
                requireArguments().getString(ARG_CURRENT_SELECTED_TIME, null)
            }else{
                savedInstanceState.getString(ARG_CURRENT_SELECTED_TIME, null)
            }
            TimePickerDelegate.setTime(binding.timePicker, currentTime)
        }

        binding.throwOffTextView.setOnClickListener {
            TimePickerDelegate.setTime(binding.timePicker,null)
            callback?.onChangeTime("", requireArguments().getInt(ARG_VIEW_ID))
            dismiss()
        }

        binding.applyTextView.setOnClickListener {
            callback?.onChangeTime(
                TimePickerDelegate.getTime(binding.timePicker),
                requireArguments().getInt(ARG_VIEW_ID)
            )
            dismiss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ARG_CURRENT_SELECTED_TIME, TimePickerDelegate.getTime(binding.timePicker))
    }

    companion object{
        const val TAG = "TimePickerBottomSheetDialog"

        private const val ARG_CURRENT_SELECTED_TIME = "CURRENT_SELECTED_TIME"
        private const val ARG_VIEW_ID = "VIEW_ID"

        fun newInstance(time: String?, viewId: Int): TimePickerBottomSheetDialog {
            return TimePickerBottomSheetDialog().apply {
                arguments = bundleOf(ARG_CURRENT_SELECTED_TIME to time, ARG_VIEW_ID to viewId)
            }
        }
    }

    interface TimePickerCallback{
        fun onChangeTime(time: String, viewId: Int)
    }
}