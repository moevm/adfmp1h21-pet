package abdulmanov.eduard.pets.presentation.event.model

import abdulmanov.eduard.pets.domain.models.event.Event
import abdulmanov.eduard.pets.domain.models.event.RepeatMode
import abdulmanov.eduard.pets.presentation._common.extensions.ifNotEmpty
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
data class EventPresentationModel(
    val id: Int = 0,
    var name: String = "",
    var repeatMode: RepeatMode = RepeatMode.DO_NOT_REPEAT,
    var date: String = "",
    var isNotification: Boolean = false,
    var time: String = "",
    val petId: Int = -1
): Parcelable {

    fun isNew() = id == 0

    companion object {

        private val DATE_FORMATTER_PRESENTER = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")

        fun fromDomain(events: List<Event>): List<EventPresentationModel> {
            return events.map(::fromDomain)
        }

        fun fromDomain(event: Event): EventPresentationModel {
            return EventPresentationModel(
                id = event.id,
                name = event.name,
                repeatMode = event.repeatMode,
                date = event.date.ifNotEmpty { DATE_FORMATTER_PRESENTER.format(LocalDate.parse(it)) },
                isNotification = event.isNotification,
                time = event.time,
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
                petId = event.petId
            )
        }
    }
}