package abdulmanov.eduard.pets.presentation.event.model

import abdulmanov.eduard.pets.domain.models.event.Event
import abdulmanov.eduard.pets.domain.models.event.RepeatMode
import abdulmanov.eduard.pets.presentation._common.extensions.ifNotEmpty
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
data class EventPresentationModel(
    val id: Int = 0,
    var name: String = "",
    var repeatMode: RepeatMode = RepeatMode.DO_NOT_REPEAT,
    var date: String = "",
    var isNotification: Boolean = false,
    var time: String = "",
    val doneDates: List<String> = emptyList(),
    val isDone: Boolean = false,
    val petId: Int = -1
): Parcelable {

    fun isNew() = id == 0

    companion object {

        private val DATE_FORMATTER_PRESENTER = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")

        fun getCalendar(event: EventPresentationModel): Calendar {
            val date = LocalDate.parse(event.date, DATE_FORMATTER_PRESENTER).toString()

            return Calendar.getInstance().apply {
                set(Calendar.YEAR, date.split("-")[0].toInt())
                set(Calendar.MONTH, date.split("-")[1].toInt() - 1)
                set(Calendar.DAY_OF_MONTH, date.split("-")[2].toInt())
                set(Calendar.HOUR_OF_DAY, event.time.split(":")[0].toInt())
                set(Calendar.MINUTE, event.time.split(":")[1].toInt())
            }
        }

        fun fromDomain(events: List<Event>, currentDate: LocalDate): List<EventPresentationModel> {
            return events.map { event ->
                fromDomain(event).copy(isDone = currentDate.toString() in event.doneDates)
            }
        }

        fun fromDomain(event: Event): EventPresentationModel {
            return EventPresentationModel(
                id = event.id,
                name = event.name,
                repeatMode = event.repeatMode,
                date = event.date.ifNotEmpty { DATE_FORMATTER_PRESENTER.format(LocalDate.parse(it)) },
                isNotification = event.isNotification,
                time = event.time,
                doneDates = event.doneDates,
                petId = event.petId
            )
        }

        fun toDomain(event: EventPresentationModel): Event {
            return Event(
                id = event.id,
                name = event.name,
                repeatMode = event.repeatMode,
                date = event.date.ifNotEmpty { LocalDate.parse(it, DATE_FORMATTER_PRESENTER).toString() },
                isNotification = event.isNotification,
                time = event.time,
                doneDates = event.doneDates,
                petId = event.petId
            )
        }
    }
}