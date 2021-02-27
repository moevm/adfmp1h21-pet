package abdulmanov.eduard.pets.presentation.pet

import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import io.reactivex.Completable
import javax.inject.Inject

class PetViewModel @Inject constructor(
    private val router: Router,
    private val petsInteractor: PetsInteractor
): BaseViewModel() {

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    lateinit var pet: PetPresentationModel
        private set

    fun setPetOrDefault(pet:PetPresentationModel?) {
        this.pet = pet ?: PetPresentationModel()
    }

    fun createOrUpdatePet(){
        if(_showApplyProgress.value != true){
            getCompletableCreateOrUpdate()
                .addDispatchers()
                .doOnSubscribe { _showApplyProgress.value = true }
                .doOnTerminate { _showApplyProgress.value = false }
                .subscribe(
                    {
                        Log.d("FuckFuck","success")
                    },
                    {
                        Log.d("FuckFuck",it.message.toString())
                    }
                )
                .connect()
        }
    }

    private fun getCompletableCreateOrUpdate(): Completable {
        val domainModel = PetPresentationModel.toDomain(pet)

        return if(pet.id == 0){
            petsInteractor.createPet(domainModel)
        }else {
            petsInteractor.updatePet(domainModel)
        }
    }
}