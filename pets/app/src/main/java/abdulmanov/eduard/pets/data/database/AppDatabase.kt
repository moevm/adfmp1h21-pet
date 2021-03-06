package abdulmanov.eduard.pets.data.database

import abdulmanov.eduard.pets.data.database.dao.EventDao
import abdulmanov.eduard.pets.data.database.dao.InterviewDao
import abdulmanov.eduard.pets.data.database.dao.PetDao
import abdulmanov.eduard.pets.data.database.models.EventDbModel
import abdulmanov.eduard.pets.data.database.models.InterviewDbModel
import abdulmanov.eduard.pets.data.database.models.PetDbModel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PetDbModel::class, EventDbModel::class, InterviewDbModel::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val petDao: PetDao

    abstract val eventDao: EventDao

    abstract val interviewDao: InterviewDao

    companion object {
        private const val DATABASE_NAME = "pets_database"

        fun getRoomDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}