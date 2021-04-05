package abdulmanov.eduard.pets.pets

import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.domain.models.pet.BirthDate
import abdulmanov.eduard.pets.domain.models.pet.Pet
import abdulmanov.eduard.pets.domain.models.pet.Sex
import abdulmanov.eduard.pets.domain.repositories.PetsRepository
import io.reactivex.Completable
import io.reactivex.Single

object PetsData {

    private val repository = object : PetsRepository {
        override fun getPets(): Single<List<Pet>> {
            return Single.fromCallable {
                listOf(
                    Pet(
                        id = 1,
                        avatar = "",
                        name = "а",
                        type = "Кошка",
                        sex = Sex.MALE,
                        birthDate = BirthDate("Сентябрь", "2021"),
                        isCurrent = false
                    ),
                    Pet(
                        id = 2,
                        avatar = "",
                        name = "б",
                        type = "Кошка",
                        sex = Sex.MALE,
                        birthDate = BirthDate("Сентябрь", "2021"),
                        isCurrent = false
                    ),
                    Pet(
                        id = 3,
                        avatar = "",
                        name = "в",
                        type = "Кошка",
                        sex = Sex.MALE,
                        birthDate = BirthDate("Сентябрь", "2021"),
                        isCurrent = false
                    ),
                    Pet(
                        id = 4,
                        avatar = "",
                        name = "г",
                        type = "Кошка",
                        sex = Sex.MALE,
                        birthDate = BirthDate("Сентябрь", "2021"),
                        isCurrent = true
                    ),
                    Pet(
                        id = 5,
                        avatar = "",
                        name = "д",
                        type = "Кошка",
                        sex = Sex.MALE,
                        birthDate = BirthDate("Сентябрь", "2021"),
                        isCurrent = false
                    ),
                    Pet(
                        id = 6,
                        avatar = "",
                        name = "е",
                        type = "Кошка",
                        sex = Sex.MALE,
                        birthDate = BirthDate("Сентябрь", "2021"),
                        isCurrent = false
                    )
                ).reversed()
            }
        }

        override fun getPetById(petId: Int): Single<Pet> {
            TODO("Not yet implemented")
        }

        override fun createPet(pet: Pet): Single<Int> {
            TODO("Not yet implemented")
        }

        override fun updatePet(pet: Pet): Completable {
            TODO("Not yet implemented")
        }

        override fun deletePet(petId: Int): Completable {
            TODO("Not yet implemented")
        }

        override fun saveIdCurrentPet(petId: Int) {
            TODO("Not yet implemented")
        }

        override fun getIdCurrentPet(): Int {
            TODO("Not yet implemented")
        }
    }

    val interactor = PetsInteractor(repository)

    val currentPet = Pet(
        id = 4,
        avatar = "",
        name = "г",
        type = "Кошка",
        sex = Sex.MALE,
        birthDate = BirthDate("Сентябрь", "2021"),
        isCurrent = true
    )

    val sortedNamePets = listOf(
        Pet(
            id = 1,
            avatar = "",
            name = "а",
            type = "Кошка",
            sex = Sex.MALE,
            birthDate = BirthDate("Сентябрь", "2021"),
            isCurrent = false
        ),
        Pet(
            id = 2,
            avatar = "",
            name = "б",
            type = "Кошка",
            sex = Sex.MALE,
            birthDate = BirthDate("Сентябрь", "2021"),
            isCurrent = false
        ),
        Pet(
            id = 3,
            avatar = "",
            name = "в",
            type = "Кошка",
            sex = Sex.MALE,
            birthDate = BirthDate("Сентябрь", "2021"),
            isCurrent = false
        ),
        Pet(
            id = 4,
            avatar = "",
            name = "г",
            type = "Кошка",
            sex = Sex.MALE,
            birthDate = BirthDate("Сентябрь", "2021"),
            isCurrent = true
        ),
        Pet(
            id = 5,
            avatar = "",
            name = "д",
            type = "Кошка",
            sex = Sex.MALE,
            birthDate = BirthDate("Сентябрь", "2021"),
            isCurrent = false
        ),
        Pet(
            id = 6,
            avatar = "",
            name = "е",
            type = "Кошка",
            sex = Sex.MALE,
            birthDate = BirthDate("Сентябрь", "2021"),
            isCurrent = false
        )
    )
}