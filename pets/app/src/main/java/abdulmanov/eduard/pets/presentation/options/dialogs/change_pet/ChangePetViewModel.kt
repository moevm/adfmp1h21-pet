package abdulmanov.eduard.pets.presentation.options.dialogs.change_pet

import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.options.dialogs.change_pet.models.AddPresentationModel
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class ChangePetViewModel @Inject constructor(
    private val router: Router,
    private val petsInteractor: PetsInteractor
) : BaseViewModel() {

    private val _items = MutableLiveData<List<Any>>()
    val items: LiveData<List<Any>>
        get() = _items

    fun openScreenCreatePet() = router.navigateTo(Screens.pet())

    fun getPets() {
        petsInteractor.getPets()
            .map(PetPresentationModel::fromDomain)
            .safeSubscribe { _items.value = it.plus(AddPresentationModel()) }
    }

    fun selectPet(pet: PetPresentationModel) {
        petsInteractor.saveIdCurrentPet(pet.id)
    }
}