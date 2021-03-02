package abdulmanov.eduard.pets.domain.repositories

import abdulmanov.eduard.pets.domain.models.Pet
import io.reactivex.Completable
import io.reactivex.Single

interface PetsRepository {

    fun getPets(): Single<List<Pet>>

    fun getPetById(petId: Int): Single<Pet>

    fun createPet(pet: Pet): Single<Int>

    fun updatePet(pet: Pet): Completable

    fun deletePet(petId: Int): Completable

    fun saveIdCurrentPet(petId: Int)

    fun getIdCurrentPet(): Int
}