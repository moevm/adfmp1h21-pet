package abdulmanov.eduard.pets.data.repositories

import abdulmanov.eduard.pets.data.database.dao.PetDao
import abdulmanov.eduard.pets.data.database.models.PetDbModel
import abdulmanov.eduard.pets.data.sharedpreferences.PetsSharedPreferences
import abdulmanov.eduard.pets.domain.models.Pet
import abdulmanov.eduard.pets.domain.repositories.PetsRepository
import io.reactivex.Completable
import io.reactivex.Single

class PetsRepositoryImpl(
    private val petDao: PetDao,
    private val sharedPreferences: PetsSharedPreferences
): PetsRepository {

    override fun getPets(): Single<List<Pet>> {
        return petDao.getPets()
            .map { pets -> pets.map { PetDbModel.toDomain(it, it.id == getIdCurrentPet()) }}
    }

    override fun createPet(pet: Pet): Single<Int> {
        val petDbModel = PetDbModel.fromDomain(pet)

        return petDao.insertPet(petDbModel).map { it.toInt() }
    }

    override fun updatePet(pet: Pet): Completable {
        val petDbModel = PetDbModel.fromDomain(pet)
        return petDao.updatePet(petDbModel)
    }

    override fun deletePet(petId: Int): Completable {
        return petDao.deleteById(petId)
    }

    override fun saveIdCurrentPet(petId: Int) {
        sharedPreferences.idCurrentPet = petId
    }

    override fun getIdCurrentPet(): Int {
        return sharedPreferences.idCurrentPet
    }
}