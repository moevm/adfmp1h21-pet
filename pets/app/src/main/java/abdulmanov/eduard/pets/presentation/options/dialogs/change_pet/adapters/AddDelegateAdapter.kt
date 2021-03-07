package abdulmanov.eduard.pets.presentation.options.dialogs.change_pet.adapters

import abdulmanov.eduard.pets.databinding.ItemPetBinding
import abdulmanov.eduard.pets.presentation.options.dialogs.change_pet.models.AddPresentationModel
import android.view.View
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class AddDelegateAdapter(
    private val clickListener: () -> Unit
) : ViewBindingDelegateAdapter<AddPresentationModel, ItemPetBinding>(ItemPetBinding::inflate) {

    override fun ItemPetBinding.onBind(item: AddPresentationModel) {
        root.setOnClickListener { clickListener.invoke() }

        avatarImageView.setImageResource(item.image)
        nameTextView.setText(item.title)
        selectedImageView.visibility = View.INVISIBLE
    }

    override fun isForViewType(item: Any) = item is AddPresentationModel

    override fun AddPresentationModel.getItemId() = title
}