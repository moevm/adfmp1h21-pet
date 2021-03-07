package abdulmanov.eduard.pets.dagger.modules

import abdulmanov.eduard.pets.data.database.AppDatabase
import abdulmanov.eduard.pets.data.database.dao.EventDao
import abdulmanov.eduard.pets.data.database.dao.PetDao
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getRoomDatabase(context)
    }

    @Singleton
    @Provides
    fun providePetDao(appDatabase: AppDatabase): PetDao {
        return appDatabase.petDao
    }

    @Singleton
    @Provides
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao
    }
}