package abdulmanov.eduard.pets.presentation.pet.model

import abdulmanov.eduard.pets.domain.models.BirthDate
import abdulmanov.eduard.pets.domain.models.Pet
import abdulmanov.eduard.pets.domain.models.Sex
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetPresentationModel(
    val id: Int = 0,
    var avatar: String = "",
    var name: String = "",
    var type: String = "",
    var sex: Sex = Sex.MALE,
    var birthDateMonth: String = "",
    var birthDateYear: String = "",
    var isCurrent: Boolean = false
) : Parcelable {

    fun isNew() = id == 0

    companion object {

        fun fromDomain(pets: List<Pet>): List<PetPresentationModel> {
            return pets.map(::fromDomain)
        }

        fun fromDomain(pet: Pet): PetPresentationModel {
            return PetPresentationModel(
                id = pet.id,
                avatar = pet.avatar,
                name = pet.name,
                type = pet.type,
                sex = pet.sex,
                birthDateMonth = pet.birthDate.month,
                birthDateYear = pet.birthDate.year,
                isCurrent = pet.isCurrent
            )
        }

        fun toDomain(pet: PetPresentationModel): Pet {
            return Pet(
                id = pet.id,
                avatar = pet.avatar,
                name = pet.name,
                type = pet.type,
                sex = pet.sex,
                birthDate = BirthDate(pet.birthDateMonth, pet.birthDateYear),
                isCurrent = pet.isCurrent
            )
        }
    }
}