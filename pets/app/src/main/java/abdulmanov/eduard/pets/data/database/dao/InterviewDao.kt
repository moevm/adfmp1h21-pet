package abdulmanov.eduard.pets.data.database.dao

import abdulmanov.eduard.pets.data.database.models.InterviewDbModel
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class InterviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertInterview(interview: InterviewDbModel): Single<Long>

    @Update
    abstract fun updateInterview(interview: InterviewDbModel): Completable

    @Query("DELETE FROM ${InterviewDbModel.TABLE_NAME} WHERE ${InterviewDbModel.COLUMN_ID} = :id")
    abstract fun deleteById(id: Int): Completable

    @Query("SELECT * FROM ${InterviewDbModel.TABLE_NAME} WHERE ${InterviewDbModel.COLUMN_ID} = :id")
    abstract fun getInterviewById(id: Int): Single<InterviewDbModel>

    @Query("SELECT * FROM ${InterviewDbModel.TABLE_NAME} WHERE ${InterviewDbModel.COLUMN_ID_PET} = :petId")
    abstract fun getInterviews(petId: Int): Single<List<InterviewDbModel>>
}