package abdulmanov.eduard.pets.presentation.calendar.dialogs.edit_event

import abdulmanov.eduard.pets.domain.interactors.EventsInteractor
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.event.model.EventPresentationModel
import androidx.lifecycle.LiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import javax.inject.Inject

class EditEventViewModel @Inject constructor(
    private val router: Router,
    private val eventsInteractor: EventsInteractor
): BaseViewModel() {

    private val _deleteEvent = LiveEvent<Unit>()
    val deleteEvent: LiveData<Unit>
        get() = _deleteEvent

    fun openScreenEditEvent(event: EventPresentationModel) = router.navigateTo(Screens.event(event.id))

    fun delete(event: EventPresentationModel) {
        eventsInteractor.deleteEvent(event.id)
            .safeSubscribe { _deleteEvent.value = Unit }
    }
}