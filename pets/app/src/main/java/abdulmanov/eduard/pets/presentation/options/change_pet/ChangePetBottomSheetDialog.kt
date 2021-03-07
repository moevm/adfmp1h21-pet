package abdulmanov.eduard.pets.presentation.options.change_pet

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.BottomDialogChangePetBinding
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseBottomSheetDialogFragment
import abdulmanov.eduard.pets.presentation.options.change_pet.adapters.AddDelegateAdapter
import abdulmanov.eduard.pets.presentation.options.change_pet.adapters.PetsDelegateAdapter
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter

class ChangePetBottomSheetDialog : BaseBottomSheetDialogFragment<BottomDialogChangePetBinding>() {

    private val viewModel by initViewModel<ChangePetViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = CompositeDelegateAdapter(
                PetsDelegateAdapter(this@ChangePetBottomSheetDialog::selectPet),
                AddDelegateAdapter(viewModel::openScreenCreatePet)
            )
        }

        viewModel.items.observe(viewLifecycleOwner, Observer(::setItems))

        if (savedInstanceState == null) {
            viewModel.getPets()
        }
    }

    private fun selectPet(pet: PetPresentationModel) {
        viewModel.selectPet(pet)
        dismiss()
    }

    private fun setItems(items: List<Any>) {
        (binding.recyclerView.adapter as CompositeDelegateAdapter).swapData(items)
    }

    companion object {
        const val TAG = "ChangePetBottomSheetDialog"

        fun newInstance() = ChangePetBottomSheetDialog()
    }
}