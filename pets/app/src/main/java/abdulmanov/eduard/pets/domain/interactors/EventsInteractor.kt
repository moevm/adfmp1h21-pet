package abdulmanov.eduard.pets.domain.interactors

import abdulmanov.eduard.pets.domain.models.event.Event
import abdulmanov.eduard.pets.domain.repositories.EventsRepository
import io.reactivex.Completable
import io.reactivex.Single

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
}