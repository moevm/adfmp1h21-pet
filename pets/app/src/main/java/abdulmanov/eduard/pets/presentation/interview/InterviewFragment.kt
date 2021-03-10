package abdulmanov.eduard.pets.presentation.interview

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentInterviewBinding
import abdulmanov.eduard.pets.domain.models.interview.Interview
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.pets.presentation._common.extensions.bind
import abdulmanov.eduard.pets.presentation._common.extensions.initSpinner
import abdulmanov.eduard.pets.presentation.interview.model.InterviewPresentationModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import java.time.LocalDate


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

        viewModel.currentInterview.observe(viewLifecycleOwner, Observer(::setInterview))
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUI(){
        binding.toolbar.run {
            setTitle(R.string.interview_toolbar_title)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
        }

        val items = requireContext().resources.getStringArray(R.array.interview_type_day).toList()
        binding.dayTypeTextView.initSpinner(items)
        binding.dayTypeTextView.bind {
            val index = items.indexOf(it)
            val dateNow = LocalDate.now()
            val currentSelectDate = dateNow.minusDays(index.toLong())
            viewModel.getInterviewForDate(currentSelectDate)
        }
        binding.dayTypeTextView.setText(items[0], false)
    }

    private fun setInterview(interview: InterviewPresentationModel){
        binding.ratingBar.setOnRatingBarChangeListener { _, _, _ -> }
        binding.ratingBar.rating = interview.rating.toFloat()
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            viewModel.createOrUpdateInterview(rating.toInt())
        }
    }

    companion object {
        fun newInstance() = InterviewFragment()
    }
}