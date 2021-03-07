package abdulmanov.eduard.pets.domain.repositories

import abdulmanov.eduard.pets.domain.models.event.Event
import io.reactivex.Completable
import io.reactivex.Single

interface EventsRepository {

    fun getEvents(): Single<List<Event>>

    fun getEventById(id: Int): Single<Event>

    fun createEvent(event: Event): Completable

    fun updateEvent(event: Event): Completable

    fun deleteEvent(eventId: Int): Completable
}