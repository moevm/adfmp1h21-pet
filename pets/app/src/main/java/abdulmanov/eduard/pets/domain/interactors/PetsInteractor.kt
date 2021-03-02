package abdulmanov.eduard.pets.domain.interactors

import abdulmanov.eduard.pets.domain.models.Pet
import abdulmanov.eduard.pets.domain.repositories.PetsRepository
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single

class PetsInteractor(private val petsRepository: PetsRepository) {

    fun getCurrentPet(): Single<Pet> {
        return petsRepository.getPets()
            .map { pets -> pets.find { it.isCurrent } }
    }

    fun getPetById(petId: Int): Single<Pet> {
        return petsRepository.getPetById(petId)
    }

    fun getPets(): Single<List<Pet>>{
        return petsRepository.getPets()
            .map { pets -> pets.sortedBy { it.name } }
    }

    fun createPet(pet: Pet): Completable {
        return petsRepository.createPet(pet)
            .doOnSuccess { petsRepository.saveIdCurrentPet(it) }
            .ignoreElement()
    }

    fun updatePet(pet: Pet): Completable {
        return petsRepository.updatePet(pet)
    }

    fun deletePet(petId: Int): Completable {
        return petsRepository.getPets()
            .map { pets -> pets.filter { it.id != petId } }
            .doOnSuccess {
                if(it.isNotEmpty()){
                    petsRepository.saveIdCurrentPet(it.first().id)
                }else{
                    petsRepository.saveIdCurrentPet(-1)
                }
            }
            .flatMapCompletable { petsRepository.deletePet(petId) }
    }

    fun getIdCurrentPet(): Int {
        return petsRepository.getIdCurrentPet()
    }
}