package abdulmanov.eduard.pets.data.database.dao

import abdulmanov.eduard.pets.data.database.models.PetDbModel
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPet(pet: PetDbModel): Single<Long>

    @Update
    abstract fun updatePet(pet: PetDbModel): Completable

    @Query("DELETE FROM ${PetDbModel.TABLE_NAME} WHERE ${PetDbModel.COLUMN_ID} = :id")
    abstract fun deleteById(id: Int): Completable

    @Query("SELECT * FROM ${PetDbModel.TABLE_NAME} WHERE ${PetDbModel.COLUMN_ID} = :id")
    abstract fun getPetById(id: Int): Single<PetDbModel>

    @Query("SELECT * FROM ${PetDbModel.TABLE_NAME}")
    abstract fun getPets(): Single<List<PetDbModel>>
}