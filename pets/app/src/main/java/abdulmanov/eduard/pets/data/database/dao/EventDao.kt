package abdulmanov.eduard.pets.data.database.dao

import abdulmanov.eduard.pets.data.database.models.EventDbModel
import abdulmanov.eduard.pets.data.database.models.PetDbModel
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEvent(event: EventDbModel): Single<Long>

    @Update
    abstract fun updateEvent(event: EventDbModel): Completable

    @Query("DELETE FROM ${EventDbModel.TABLE_NAME} WHERE ${EventDbModel.COLUMN_ID} = :id")
    abstract fun deleteById(id: Int): Completable

    @Query("SELECT * FROM ${EventDbModel.TABLE_NAME} WHERE ${EventDbModel.COLUMN_ID} = :id")
    abstract fun getEventById(id: Int): Single<EventDbModel>

    @Query("SELECT * FROM ${EventDbModel.TABLE_NAME} WHERE ${EventDbModel.COLUMN_ID_PET} = :petId")
    abstract fun getEvents(petId: Int): Single<List<EventDbModel>>
}