package abdulmanov.eduard.pets.presentation.event


import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val router: Router
) : BaseViewModel() {

    fun onBackCommandClick() = router.exit()
}