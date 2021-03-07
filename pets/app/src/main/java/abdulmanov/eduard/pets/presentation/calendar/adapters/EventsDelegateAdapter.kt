package abdulmanov.eduard.pets.presentation.calendar.adapters

import abdulmanov.eduard.pets.databinding.ItemEventBinding
import abdulmanov.eduard.pets.presentation.event.model.EventPresentationModel
import android.graphics.Paint
import android.widget.TextView
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class EventsDelegateAdapter(
    private val clickListener: (EventPresentationModel, Boolean) -> Unit,
    private val longClickListener: (EventPresentationModel) -> Unit
) : ViewBindingDelegateAdapter<EventPresentationModel, ItemEventBinding>(ItemEventBinding::inflate) {

    override fun ItemEventBinding.onBind(item: EventPresentationModel) {
        root.setOnClickListener {
            checkbox.isChecked = !checkbox.isChecked
            nameTextView.setCrossOut(checkbox.isChecked)
            clickListener.invoke(item, checkbox.isChecked)
        }

        root.setOnLongClickListener {
            longClickListener.invoke(item)
            true
        }

        checkbox.isChecked = item.isDone
        nameTextView.text = item.name
        nameTextView.setCrossOut(item.isDone)
    }

    private fun TextView.setCrossOut(isCrossOut: Boolean){
        paintFlags = if(isCrossOut) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else 0
    }

    override fun isForViewType(item: Any) = item is EventPresentationModel

    override fun EventPresentationModel.getItemId() = id
}