package abdulmanov.eduard.pets.presentation.calendar.adapters

import abdulmanov.eduard.pets.databinding.ItemEventBinding
import abdulmanov.eduard.pets.presentation.event.model.EventPresentationModel
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class EventsDelegateAdapter(
    private val clickListener: (EventPresentationModel) -> Unit,
    private val longClickListener: (EventPresentationModel) -> Unit
) : ViewBindingDelegateAdapter<EventPresentationModel, ItemEventBinding>(ItemEventBinding::inflate) {

    override fun ItemEventBinding.onBind(item: EventPresentationModel) {
        root.setOnClickListener { clickListener.invoke(item) }
        root.setOnLongClickListener {
            longClickListener.invoke(item)
            true
        }

        nameTextView.text = item.name
    }

    override fun isForViewType(item: Any) = item is EventPresentationModel

    override fun EventPresentationModel.getItemId() = id
}