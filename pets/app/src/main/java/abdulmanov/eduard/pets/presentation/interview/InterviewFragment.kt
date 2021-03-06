package abdulmanov.eduard.pets.presentation.interview

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentInterviewBinding
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter

class InterviewFragment : BaseFragment<FragmentInterviewBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUI(){
        val items = requireContext().resources.getStringArray(R.array.interview_type_day).toList()
        binding.dayTypeTextView.setAdapter(ArrayAdapter(requireContext(), R.layout.item_spinner, items))
        binding.dayTypeTextView.setOnTouchListener { view, motionEvent ->
            return@setOnTouchListener true
        }
        binding.dayTypeTextView.setText(items[0], false)

        binding.toolbar.run {
            setTitle(R.string.interview_toolbar_title)
            setNavigationIcon(R.drawable.ic_arrow_back_white)
            setBackgroundColor(resources.getColor(R.color.colorControlActive))
            setTitleTextColor(resources.getColor(R.color.colorPrimary))
            setNavigationOnClickListener {
                // Todo: Add jump back
            }
        }
    }

    companion object {
        fun newInstance() = InterviewFragment()
    }
}