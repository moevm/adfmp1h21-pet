package abdulmanov.eduard.pets.presentation.options

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentOptionsBinding
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.pets.presentation.options.dialogs.change_pet.ChangePetBottomSheetDialog
import android.content.Context
import android.os.Bundle
import android.view.View

class OptionsFragment : BaseFragment<FragmentOptionsBinding>() {

    private val viewModel by initViewModel<OptionsViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.toolbar.run {
            setTitle(R.string.options_toolbar_title)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
        }

        binding.editPetItemOption.titleTextView.setText(R.string.options_edit_pet)
        binding.editPetItemOption.root.setOnClickListener { viewModel.openScreenEditPet() }

        binding.changePetItemOption.titleTextView.setText(R.string.options_change_pet)
        binding.changePetItemOption.root.setOnClickListener { openChangePetDialog() }

        binding.interviewItemOption.titleTextView.setText(R.string.options_interview)
        binding.interviewItemOption.root.setOnClickListener { viewModel.openScreenInterview() }

        binding.statisticItemOption.titleTextView.setText(R.string.options_statistic)
        binding.statisticItemOption.root.setOnClickListener { viewModel.openScreenStatistic() }
    }

    private fun openChangePetDialog() {
        val dialog = ChangePetBottomSheetDialog.newInstance()
        dialog.show(childFragmentManager, ChangePetBottomSheetDialog.TAG)
    }

    companion object {
        fun newInstance() = OptionsFragment()
    }
}