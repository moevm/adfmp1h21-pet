package abdulmanov.eduard.pets.presentation.main

import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router,
    private val petsInteractor: PetsInteractor
) : BaseViewModel() {

    fun executeTransitionProcessing() {
        if (petsInteractor.getIdCurrentPet() == -1) {
            router.replaceScreen(Screens.pet())
        } else {
            router.replaceScreen(Screens.calendar())
        }
    }
}