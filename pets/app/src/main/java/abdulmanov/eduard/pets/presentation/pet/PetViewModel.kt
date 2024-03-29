package abdulmanov.eduard.pets.presentation.pet

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.FilesProvider
import abdulmanov.eduard.pets.presentation._common.StringProvider
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PetViewModel @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val filesProvider: FilesProvider,
    private val petsInteractor: PetsInteractor
) : BaseViewModel() {

    private val _showApplyProgress = MutableLiveData(false)
    val showApplyProgress: LiveData<Boolean>
        get() = _showApplyProgress

    private val _showDeleteProgress = MutableLiveData(false)
    val showDeleteProgress: LiveData<Boolean>
        get() = _showApplyProgress

    private val _initializationFieldsEvent = LiveEvent<Unit>()
    val initializationFieldsEvent: LiveData<Unit>
        get() = _initializationFieldsEvent

    private val _showMessageEvent = LiveEvent<String>()
    val showMessageEvent: LiveData<String>
        get() = _showMessageEvent

    var pet: PetPresentationModel? = null

    fun setPetOrDefault(petId: Int) {
        getSinglePet(petId).safeSubscribe {
            this.pet = it
            _initializationFieldsEvent.value = Unit
        }
    }

    fun saveImageToInternalStorage(uri: Uri){
        Single.zip(
            filesProvider.getInputStreamForImageInGallery(uri),
            filesProvider.getNewFileFromCache(),
            { input, file ->
                file.outputStream().use { output ->
                    output.write(input.readBytes())
                }
                file
            }
        ).safeSubscribe { pet!!.avatar = Uri.fromFile(it).toString() }
    }

    fun createOrUpdatePet() {
        if (_showApplyProgress.value != true && pet != null) {
            if(pet!!.name.isNotEmpty()) {
                getCompletableCreateOrUpdate()
                    .addDispatchers()
                    .doOnSubscribe { _showApplyProgress.value = true }
                    .doOnTerminate { _showApplyProgress.value = false }
                    .subscribe {
                        if (pet!!.isNew()) {
                            router.newRootScreen(Screens.calendar())
                        } else {
                            router.exit()
                        }
                    }
                    .connect()
            } else {
                _showMessageEvent.value = stringProvider.getString(R.string.pet_error)
            }
        }
    }

    fun deletePet() {
        if (_showDeleteProgress.value != true && pet != null) {
            petsInteractor.deletePet(pet!!.id)
                .addDispatchers()
                .doOnSubscribe { _showDeleteProgress.value = true }
                .doOnTerminate { _showDeleteProgress.value = false }
                .subscribe {
                    if (petsInteractor.getIdCurrentPet() != -1) {
                        router.exit()
                    } else {
                        router.newRootChain(Screens.pet())
                    }
                }
                .connect()
        }
    }

    private fun getCompletableCreateOrUpdate(): Completable {
        val domainModel = PetPresentationModel.toDomain(pet!!)

        return if (pet!!.isNew()) {
            petsInteractor.createPet(domainModel)
        } else {
            petsInteractor.updatePet(domainModel)
        }
    }

    private fun getSinglePet(petId: Int): Single<PetPresentationModel> {
        return if (petId == -1) {
            Single.just(PetPresentationModel())
        } else {
            petsInteractor.getPetById(petId).map(PetPresentationModel::fromDomain)
        }
    }

}