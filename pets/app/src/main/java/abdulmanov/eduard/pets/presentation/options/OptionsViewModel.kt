package abdulmanov.eduard.pets.presentation.options

import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class OptionsViewModel @Inject constructor(
    private val router: Router,
    private val petsInteractor: PetsInteractor
): BaseViewModel() {

    fun onBackCommandClick() = router.exit()

    fun openScreenEditPet(){
        val petId = petsInteractor.getIdCurrentPet()
        router.navigateTo(Screens.pet(petId))
    }

    fun openScreenInterview() {

    }

    fun openScreenStatistic() {

    }
}