package abdulmanov.eduard.pets.presentation.calendar

import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val router: Router
): BaseViewModel() {

    fun openScreenOptions() = router.navigateTo(Screens.options())
}