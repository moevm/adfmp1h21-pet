package abdulmanov.eduard.pets.presentation.pet

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentPetBinding
import abdulmanov.eduard.pets.domain.models.BirthDate
import abdulmanov.eduard.pets.domain.models.Sex
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.bind
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

        if (savedInstanceState == null) {
            viewModel.setPetOrDefault(petId)
        }
    }

    private fun initUI() {
        Log.d("FuckFuck", "init")
        binding.avatarImageView.setOnClickListener {
            pickImage()
        }

        initSpinner(binding.typeTextView, requireContext().resources.getStringArray(R.array.pets_type).toList())
        initSpinner(binding.yearTextView, BirthDate.getYears())
        initSpinner(binding.monthTextView, BirthDate.getMonths())

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
        setAvatar(viewModel.pet!!.avatar)

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
            viewModel.pet?.avatar = result.data!!.data.toString()
            setAvatar(viewModel.pet!!.avatar)
        }
    }

    private fun setAvatar(avatar: String?) {
        if (!avatar.isNullOrEmpty()) {
            val uri = Uri.parse(avatar)
            binding.avatarImageView.setImageURI(uri)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initSpinner(autoCompleteTextView: AutoCompleteTextView, items: List<String>) {
        autoCompleteTextView.setAdapter(ArrayAdapter(requireContext(), R.layout.item_spinner, items))
        autoCompleteTextView.setOnTouchListener { _, _ -> return@setOnTouchListener true }
    }

    companion object {
        private const val ARG_ID_PET = "pet"

        fun newInstance(petId: Int): PetFragment {
            return PetFragment().apply {
                arguments = bundleOf(ARG_ID_PET to petId)
            }
        }
    }
}