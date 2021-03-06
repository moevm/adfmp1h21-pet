package abdulmanov.eduard.pets.data.database.models

import abdulmanov.eduard.pets.data.database.models.PetDbModel.Companion.TABLE_NAME
import abdulmanov.eduard.pets.domain.models.pet.BirthDate
import abdulmanov.eduard.pets.domain.models.pet.Pet
import abdulmanov.eduard.pets.domain.models.pet.Sex
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_NAME)
data class PetDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int = 0,
    @ColumnInfo(name = COLUMN_AVATAR)
    val avatar: String,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_TYPE)
    val type: String,
    @ColumnInfo(name = COLUMN_SEX)
    val sex: Int,
    @ColumnInfo(name = COLUMN_BIRTH_DATE)
    val birthDate: String
) {
    companion object {
        const val TABLE_NAME = "pet"

        const val COLUMN_ID = "pet_id"
        const val COLUMN_AVATAR = "pet_avatar"
        const val COLUMN_NAME = "pet_name"
        const val COLUMN_TYPE = "pet_type"
        const val COLUMN_SEX = "pet_sex"
        const val COLUMN_BIRTH_DATE = "pet_birth_date"

        fun toDomain(pet: PetDbModel, isCurrent: Boolean): Pet {
            return Pet(
                id = pet.id,
                avatar = pet.avatar,
                name = pet.name,
                type = pet.type,
                sex = Sex.getSexFromValue(pet.sex),
                birthDate = BirthDate.fromString(pet.birthDate),
                isCurrent = isCurrent
            )
        }

        fun fromDomain(pet: Pet): PetDbModel {
            return PetDbModel(
                id = pet.id,
                avatar = pet.avatar,
                name = pet.name,
                type = pet.type,
                sex = pet.sex.value,
                birthDate = pet.birthDate.toString()
            )
        }
    }
}