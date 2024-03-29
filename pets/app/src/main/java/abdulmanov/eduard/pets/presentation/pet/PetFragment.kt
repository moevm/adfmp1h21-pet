package abdulmanov.eduard.pets.presentation.pet

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentPetBinding
import abdulmanov.eduard.pets.domain.models.pet.BirthDate
import abdulmanov.eduard.pets.domain.models.pet.Sex
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.bind
import abdulmanov.eduard.pets.presentation._common.extensions.initSpinner
import abdulmanov.eduard.pets.presentation._common.extensions.loadImg
import abdulmanov.eduard.pets.presentation._common.extensions.setMultiLineCapSentencesAndDoneAction
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer

class PetFragment : BaseFragment<FragmentPetBinding>() {

    private val viewModel by initViewModel<PetViewModel>()

    private val imageStartForResult = registerForActivityResult(StartActivityForResult(), ::onImageResult)

    private val petId: Int by lazy { requireArguments().getInt(ARG_ID_PET) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showApplyProgress.observe(viewLifecycleOwner, Observer(binding.applyProgressButton::showProgress))
        viewModel.showDeleteProgress.observe(viewLifecycleOwner, Observer(binding.deleteProgressButton::showProgress))
        viewModel.initializationFieldsEvent.observe(viewLifecycleOwner, { initFields() })
        viewModel.showMessageEvent.observe(viewLifecycleOwner, Observer(::showMessage))

        if (savedInstanceState == null) {
            viewModel.setPetOrDefault(petId)
        }
    }

    private fun initUI() {
        binding.avatarImageView.setOnClickListener {
            pickImage()
        }

        binding.typeTextView.initSpinner(requireContext().resources.getStringArray(R.array.pets_type).toList())
        binding.yearTextView.initSpinner(BirthDate.getYears())
        binding.monthTextView.initSpinner(requireContext().resources.getStringArray(R.array.months_nominative).toList())

        binding.nameTextInputEditText.setMultiLineCapSentencesAndDoneAction()

        binding.nameTextInputEditText.bind { viewModel.pet?.name = it }
        binding.typeTextView.bind { viewModel.pet?.type = it }
        binding.yearTextView.bind { viewModel.pet?.birthDateYear = it }
        binding.monthTextView.bind { viewModel.pet?.birthDateMonth = it }
        binding.sexRadioGroup.bind({ viewModel.pet?.sex = it as Sex }, {
            when (it) {
                R.id.maleRadioButton -> Sex.MALE
                R.id.femaleRadioButton -> Sex.FEMALE
                else -> Sex.MALE
            }
        })

        binding.deleteProgressButton.isVisible = petId != -1
        binding.deleteProgressButton.setOnClickListener {
            viewModel.deletePet()
        }

        binding.applyProgressButton.setText(if (petId == -1) R.string.pet_button_create else R.string.pet_button_save)
        binding.applyProgressButton.setOnClickListener {
            viewModel.createOrUpdatePet()
        }
    }

    private fun initFields() {
        binding.avatarImageView.loadImg(viewModel.pet!!.avatar)

        binding.nameTextInputEditText.setText(viewModel.pet!!.name)
        binding.typeTextView.setText(viewModel.pet!!.type, false)
        binding.yearTextView.setText(viewModel.pet!!.birthDateYear, false)
        binding.monthTextView.setText(viewModel.pet!!.birthDateMonth, false)

        when (viewModel.pet!!.sex) {
            Sex.MALE -> binding.maleRadioButton.isChecked = true
            Sex.FEMALE -> binding.femaleRadioButton.isChecked = true
        }
        binding.sexRadioGroup.jumpDrawablesToCurrentState()
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imageStartForResult.launch(intent)
    }

    private fun onImageResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.saveImageToInternalStorage(result.data!!.data!!)
            binding.avatarImageView.loadImg(result.data!!.data!!.toString())
        }
    }

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ARG_ID_PET = "id_pet"

        fun newInstance(petId: Int): PetFragment {
            return PetFragment().apply {
                arguments = bundleOf(ARG_ID_PET to petId)
            }
        }
    }
}