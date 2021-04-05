package abdulmanov.eduard.pets.domain.repositories

import abdulmanov.eduard.pets.domain.models.event.Event
import io.reactivex.Completable
import io.reactivex.Single

interface EventsRepository {

    fun getEvents(): Single<List<Event>>

    fun getEventById(id: Int): Single<Event>

    fun createEvent(event: Event): Single<Event>

    fun updateEvent(event: Event): Single<Event>

    fun deleteEvent(eventId: Int): Completable
}