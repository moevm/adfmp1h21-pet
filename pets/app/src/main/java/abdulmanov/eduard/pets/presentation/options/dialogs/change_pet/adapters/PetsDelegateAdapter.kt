package abdulmanov.eduard.pets.presentation.options.dialogs.change_pet.adapters

import abdulmanov.eduard.pets.databinding.ItemPetBinding
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import android.net.Uri
import android.view.View
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class PetsDelegateAdapter(
    private val clickListener: (PetPresentationModel) -> Unit
) : ViewBindingDelegateAdapter<PetPresentationModel, ItemPetBinding>(ItemPetBinding::inflate) {

    override fun ItemPetBinding.onBind(item: PetPresentationModel) {
        root.setOnClickListener { clickListener.invoke(item) }

        if (item.avatar.isNotEmpty()) {
            val uri = Uri.parse(item.avatar)
            avatarImageView.setImageURI(uri)
        }

        nameTextView.text = item.name
        selectedImageView.visibility = if (item.isCurrent) View.VISIBLE else View.INVISIBLE
    }

    override fun isForViewType(item: Any) = item is PetPresentationModel

    override fun PetPresentationModel.getItemId() = id
}