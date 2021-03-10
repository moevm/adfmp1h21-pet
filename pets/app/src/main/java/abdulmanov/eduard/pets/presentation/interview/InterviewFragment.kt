package abdulmanov.eduard.pets.presentation.interview

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentInterviewBinding
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.addOnBackPressedCallback
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.content.Context


class InterviewFragment : BaseFragment<FragmentInterviewBinding>() {

    private val viewModel by initViewModel<InterviewViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

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
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
        }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            // Todo: Add rating pet mood
        }
    }

    companion object {
        fun newInstance() = InterviewFragment()
    }
}