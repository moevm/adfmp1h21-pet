package abdulmanov.eduard.pets.domain.interactors

import abdulmanov.eduard.pets.domain.models.event.Event
import abdulmanov.eduard.pets.domain.models.event.RepeatMode
import abdulmanov.eduard.pets.domain.repositories.EventsRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate

class EventsInteractor(private val eventsRepository: EventsRepository) {

    fun getEventById(id: Int): Single<Event>{
        return eventsRepository.getEventById(id)
    }

    fun createEvent(event: Event): Completable {
        return eventsRepository.createEvent(event)
    }

    fun updateEvent(event: Event): Completable {
        return eventsRepository.updateEvent(event)
    }

    fun deleteEvent(eventId: Int): Completable {
        return eventsRepository.deleteEvent(eventId)
    }

    fun getEventsForSelectedDate(date: LocalDate): Single<List<Event>>{
        return eventsRepository.getEvents()
            .map { events ->
                events.filter {
                    when(it.repeatMode){
                        RepeatMode.DO_NOT_REPEAT -> {
                            it.date == date.toString()
                        }
                        RepeatMode.REPEAT_EVERY_DAY -> {
                            true
                        }
                        RepeatMode.REPEAT_EVERY_WEEK -> {
                            val dateEvent = LocalDate.parse(it.date)
                            date.dayOfWeek.value == dateEvent.dayOfWeek.value
                        }
                        RepeatMode.REPEAT_EVERY_MONTH -> {
                            //TODO корнер кейсы
                            val dateEvent = LocalDate.parse(it.date)
                            date.dayOfMonth == dateEvent.dayOfMonth
                        }
                    }
                }
            }
    }
}