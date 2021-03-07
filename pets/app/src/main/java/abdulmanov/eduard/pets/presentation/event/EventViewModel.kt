package abdulmanov.eduard.pets.presentation.event


import abdulmanov.eduard.pets.domain.interactors.EventsInteractor
import abdulmanov.eduard.pets.domain.repositories.EventsRepository
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.event.model.EventPresentationModel
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val router: Router,
    private val eventsInteractor: EventsInteractor
) : BaseViewModel() {

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    private val _initializationFieldsEvent = LiveEvent<Unit>()
    val initializationFieldsEvent: LiveData<Unit>
        get() = _initializationFieldsEvent

    var event: EventPresentationModel? = null

    fun onBackCommandClick() = router.exit()

    fun setEventOrDefault(eventId: Int) {
        getSingleEvent(eventId).safeSubscribe {
            this.event = it
            _initializationFieldsEvent.value = Unit
        }
    }

    fun createOrUpdateEvent(){
        if (_showApplyProgress.value != true && event != null) {
            getCompletableCreateOrUpdate()
                .addDispatchers()
                .doOnSubscribe { _showApplyProgress.value = true }
                .doOnTerminate { _showApplyProgress.value = false }
                .subscribe { router.exit() }
                .connect()
        }
    }

    private fun getCompletableCreateOrUpdate(): Completable {
        val domainModel = EventPresentationModel.toDomain(event!!)

        return if (event!!.isNew()) {
            eventsInteractor.createEvent(domainModel)
        } else {
            eventsInteractor.updateEvent(domainModel)
        }
    }

    private fun getSingleEvent(eventId: Int): Single<EventPresentationModel> {
        return if (eventId == -1) {
            Single.just(EventPresentationModel())
        } else {
            eventsInteractor.getEventById(eventId)
                .map(EventPresentationModel::fromDomain)
        }
    }
}