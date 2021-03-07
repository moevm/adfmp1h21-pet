package abdulmanov.eduard.pets.presentation.calendar

import abdulmanov.eduard.pets.domain.interactors.EventsInteractor
import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.event.model.EventPresentationModel
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import android.icu.util.LocaleData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import java.time.LocalDate
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val router: Router,
    private val petsInteractor: PetsInteractor,
    private val eventsInteractor: EventsInteractor
) : BaseViewModel() {

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate>
        get() = _selectedDate

    private val _currentPet = MutableLiveData<PetPresentationModel>()
    val currentPet: LiveData<PetPresentationModel>
        get() = _currentPet

    private val _events = MutableLiveData<List<EventPresentationModel>>()
    val events: LiveData<List<EventPresentationModel>>
        get() = _events

    fun openScreenOptions() = router.navigateTo(Screens.options())

    fun openScreenEvent(eventId: Int = -1) = router.navigateTo(Screens.event(eventId))

    fun setSelectedDate(selectedDate: LocalDate){
        if(_selectedDate.value != selectedDate) {
            _selectedDate.value = selectedDate
        }
    }

    fun getCurrentPet(){
        petsInteractor.getCurrentPet()
            .map(PetPresentationModel::fromDomain)
            .safeSubscribe { _currentPet.value = it }
    }

    fun getEventsForSelectedDate(date: LocalDate) {
        eventsInteractor.getEventsForSelectedDate(date)
            .map(EventPresentationModel::fromDomain)
            .safeSubscribe { _events.value = it }
    }
}