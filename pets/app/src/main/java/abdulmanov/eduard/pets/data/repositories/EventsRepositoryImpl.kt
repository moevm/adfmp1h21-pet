package abdulmanov.eduard.pets.data.repositories

import abdulmanov.eduard.pets.data.database.dao.EventDao
import abdulmanov.eduard.pets.data.database.models.EventDbModel
import abdulmanov.eduard.pets.data.sharedpreferences.PetsSharedPreferences
import abdulmanov.eduard.pets.domain.models.event.Event
import abdulmanov.eduard.pets.domain.repositories.EventsRepository
import io.reactivex.Completable
import io.reactivex.Single

class EventsRepositoryImpl(
    private val eventDao: EventDao,
    private val sharedPreferences: PetsSharedPreferences
): EventsRepository {

    override fun getEvents(): Single<List<Event>> {
        return eventDao.getEvents(sharedPreferences.idCurrentPet)
            .map(EventDbModel::toDomain)
    }

    override fun getEventById(id: Int): Single<Event> {
        return eventDao.getEventById(id)
            .map(EventDbModel::toDomain)
    }

    override fun createEvent(event: Event): Single<Event> {
        val dbModel = EventDbModel.fromDomain(event, sharedPreferences.idCurrentPet)

        return eventDao.insertEvent(dbModel)
            .flatMap { eventDao.getEventById(it.toInt()) }
            .map(EventDbModel::toDomain)
    }

    override fun updateEvent(event: Event): Single<Event> {
        val dbModel = EventDbModel.fromDomain(event)

        return eventDao.updateEvent(dbModel)
            .andThen(eventDao.getEventById(event.id))
            .map(EventDbModel::toDomain)
    }

    override fun deleteEvent(eventId: Int): Completable {
        return eventDao.deleteById(eventId)
    }
}