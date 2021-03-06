package abdulmanov.eduard.pets.presentation

import abdulmanov.eduard.pets.presentation.calendar.CalendarFragment
import abdulmanov.eduard.pets.presentation.interview.InterviewFragment
import abdulmanov.eduard.pets.presentation.options.OptionsFragment
import abdulmanov.eduard.pets.presentation.pet.PetFragment
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun pet(petId: Int = -1) = FragmentScreen {
        PetFragment.newInstance(petId)
    }

    fun calendar() = FragmentScreen {
        CalendarFragment.newInstance()
    }

    fun options() = FragmentScreen {
        OptionsFragment.newInstance()
    }

    fun interview() = FragmentScreen {
        InterviewFragment.newInstance()
    }
}