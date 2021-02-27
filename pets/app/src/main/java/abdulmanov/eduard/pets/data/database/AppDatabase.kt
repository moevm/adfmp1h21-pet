package abdulmanov.eduard.pets.data.database

import abdulmanov.eduard.pets.data.database.dao.PetDao
import abdulmanov.eduard.pets.data.database.models.PetDbModel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PetDbModel::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract val petDao: PetDao

    companion object {
        private const val DATABASE_NAME = "pets_database"

        fun getRoomDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}