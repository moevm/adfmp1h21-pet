package abdulmanov.eduard.pets.data.database.models

import abdulmanov.eduard.pets.data.database.models.InterviewDbModel.Companion.TABLE_NAME
import abdulmanov.eduard.pets.domain.models.interview.Interview
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = PetDbModel::class,
            parentColumns = [PetDbModel.COLUMN_ID],
            childColumns = [InterviewDbModel.COLUMN_ID_PET],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class InterviewDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int = 0,
    @ColumnInfo(name = COLUMN_RATING)
    val rating: Int,
    @ColumnInfo(name = COLUMN_DATE)
    val date: String,
    @ColumnInfo(name = COLUMN_ID_PET)
    val petId: Int

) {
    companion object {
        const val TABLE_NAME = "interview"

        const val COLUMN_ID = "interview_id"
        const val COLUMN_RATING = "interview_rating"
        const val COLUMN_DATE = "interview_date"
        const val COLUMN_ID_PET = "interview_id_pet"

        fun toDomain(interviews : List<InterviewDbModel>): List<Interview> {
            return interviews.map(::toDomain)
        }

        fun toDomain(interview: InterviewDbModel): Interview {
            return Interview(
                id = interview.id,
                rating = interview.rating,
                date = interview.date,
                petId = interview.petId
            )
        }

        fun fromDomain(interview: Interview, petId:Int = interview.petId): InterviewDbModel {
            return InterviewDbModel(
                id = interview.id,
                rating = interview.rating,
                date = interview.date,
                petId = petId
            )
        }
    }
}