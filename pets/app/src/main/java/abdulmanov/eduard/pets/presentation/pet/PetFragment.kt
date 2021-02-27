package abdulmanov.eduard.pets.presentation.pet

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentPetBinding
import abdulmanov.eduard.pets.domain.models.BirthDate
import abdulmanov.eduard.pets.domain.models.Sex
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.bind
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer

class PetFragment: BaseFragment<FragmentPetBinding>() {

    private val viewModel by initViewModel<PetViewModel>()

    private val imageStartForResult = registerForActivityResult(StartActivityForResult(),::onImageResult)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null){
            viewModel.setPetOrDefault(requireArguments().getParcelable(ARG_PET))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.showApplyProgress.observe(viewLifecycleOwner, Observer(::showApplyProgress))
    }

    private fun initUI(){
        initFields()

        binding.avatarImageView.setOnClickListener {
            pickImage()
        }

        initSpinner(binding.typeTextView, requireContext().resources.getStringArray(R.array.pets_type).toList())
        initSpinner(binding.yearTextView, BirthDate.getYears())
        initSpinner(binding.monthTextView, BirthDate.getMonths())

        binding.nameTextInputEditText.bind(viewModel.pet::name)
        binding.typeTextView.bind(viewModel.pet::type)
        binding.yearTextView.bind(viewModel.pet::birthDateYear)
        binding.monthTextView.bind(viewModel.pet::birthDateMonth)
        binding.sexRadioGroup.bind(viewModel.pet::sex){
            when(it){
                R.id.maleRadioButton -> Sex.MALE
                R.id.femaleRadioButton -> Sex.FEMALE
                else -> Sex.MALE
            }
        }

        binding.applyContainer.setOnClickListener {
            viewModel.createOrUpdatePet()
        }
    }

    private fun initFields(){
        setAvatar(viewModel.pet.avatar)

        binding.nameTextInputEditText.setText(viewModel.pet.name)
        binding.typeTextView.setText(viewModel.pet.type)
        binding.yearTextView.setText(viewModel.pet.birthDateYear)
        binding.monthTextView.setText(viewModel.pet.birthDateMonth)

        when(viewModel.pet.sex){
            Sex.MALE -> binding.maleRadioButton.isChecked = true
            Sex.FEMALE -> binding.femaleRadioButton.isChecked = true
        }
    }

    private fun pickImage(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imageStartForResult.launch(intent)
    }

    private fun onImageResult(result: ActivityResult){
        if(result.resultCode == Activity.RESULT_OK) {
            viewModel.pet.avatar = result.data!!.data.toString()
            setAvatar(viewModel.pet.avatar)
        }
    }

    private fun setAvatar(avatar: String){
        if(avatar.isNotEmpty()){
            val uri = Uri.parse(avatar)
            binding.avatarImageView.setImageURI(uri)
        }
    }

    private fun initSpinner(view: AutoCompleteTextView, items: List<String>){
        view.setAdapter(ArrayAdapter(requireContext(), R.layout.item_spinner, items))
    }

    private fun showApplyProgress(show:Boolean){
        binding.applyTextView.isVisible = !show
        binding.applyProgressBar.isVisible = show
    }

    companion object {
        private const val ARG_PET = "pet"

        fun newInstance(pet: PetPresentationModel?): PetFragment {
            return PetFragment().apply {
                arguments = bundleOf(ARG_PET to pet)
            }
        }
    }
}