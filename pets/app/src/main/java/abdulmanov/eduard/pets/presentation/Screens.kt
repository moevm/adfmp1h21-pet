package abdulmanov.eduard.pets.presentation

import abdulmanov.eduard.pets.presentation.pet.PetFragment
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun pet(pet: PetPresentationModel? = null) = FragmentScreen {
        PetFragment.newInstance(pet)
    }
}