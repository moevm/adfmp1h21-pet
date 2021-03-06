package abdulmanov.eduard.pets.presentation.interview

import abdulmanov.eduard.pets.databinding.FragmentInterviewBinding
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import android.os.Bundle
import android.view.View

class InterviewFragment : BaseFragment<FragmentInterviewBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.test.setText("Hello, World!")
    }

    companion object {
        fun newInstance() = InterviewFragment()
    }
}