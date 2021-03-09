package abdulmanov.eduard.pets.data.database.models

import abdulmanov.eduard.pets.data.database.models.EventDbModel.Companion.TABLE_NAME
import abdulmanov.eduard.pets.domain.models.event.Event
import abdulmanov.eduard.pets.domain.models.event.RepeatMode
import abdulmanov.eduard.pets.domain.models.pet.BirthDate
import abdulmanov.eduard.pets.domain.models.pet.Pet
import abdulmanov.eduard.pets.domain.models.pet.Sex
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
            childColumns = [EventDbModel.COLUMN_ID_PET],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EventDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int = 0,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_REPEAT_MODE)
    val repeatMode: Int,
    @ColumnInfo(name = COLUMN_DATE)
    val date: String,
    @ColumnInfo(name = COLUMN_IS_NOTIFICATION)
    val isNotification: Boolean,
    @ColumnInfo(name = COLUMN_TIME)
    val time: String,
    @ColumnInfo(name = COLUMN_DONE_DATES)
    val doneDates: String = "",
    @ColumnInfo(name = COLUMN_ID_PET)
    val petId: Int
){
    companion object {
        const val TABLE_NAME = "event"

        const val COLUMN_ID = "event_id"
        const val COLUMN_NAME = "event_name"
        const val COLUMN_REPEAT_MODE = "event_repeat_mode"
        const val COLUMN_DATE = "event_date"
        const val COLUMN_IS_NOTIFICATION = "event_is_notification"
        const val COLUMN_TIME = "event_time"
        const val COLUMN_DONE_DATES = "event_done_dates"
        const val COLUMN_ID_PET = "event_id_pet"

        fun toDomain(events : List<EventDbModel>): List<Event> {
            return events.map(::toDomain)
        }

        fun toDomain(event: EventDbModel): Event {
            return Event(
                id = event.id,
                name = event.name,
                repeatMode = RepeatMode.getRepeatModeFromValue(event.repeatMode),
                date = event.date,
                isNotification = event.isNotification,
                time = event.time,
                doneDates = if(event.doneDates.isNotEmpty()){
                    event.doneDates.split(";" )
                } else {
                    emptyList()
                },
                petId = event.petId
            )
        }

        fun fromDomain(event: Event, petId:Int = event.petId): EventDbModel {
            return EventDbModel(
                id = event.id,
                name = event.name,
                repeatMode = event.repeatMode.value,
                date = event.date,
                isNotification = event.isNotification,
                time = event.time,
                doneDates = if(event.doneDates.isNotEmpty()) {
                    event.doneDates.joinToString(";", "", "")
                } else {
                    ""
                },
                petId = petId
            )
        }
    }
}