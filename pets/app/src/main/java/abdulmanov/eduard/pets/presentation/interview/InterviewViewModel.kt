package abdulmanov.eduard.pets.presentation.interview

import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class InterviewViewModel @Inject constructor(
    private val router: Router,
) : BaseViewModel() {

    fun onBackCommandClick() = router.exit()
}