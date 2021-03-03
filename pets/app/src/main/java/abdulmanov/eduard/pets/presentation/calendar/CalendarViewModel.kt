package abdulmanov.eduard.pets.presentation.calendar

import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val router: Router,
    private val petsInteractor: PetsInteractor
) : BaseViewModel() {

    private val _currentPet = MutableLiveData<PetPresentationModel>()
    val currentPet: LiveData<PetPresentationModel>
        get() = _currentPet

    fun openScreenOptions() = router.navigateTo(Screens.options())

    fun getCurrentPet(){
        petsInteractor.getCurrentPet()
            .map(PetPresentationModel::fromDomain)
            .safeSubscribe { _currentPet.value = it }
    }
}