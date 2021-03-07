package abdulmanov.eduard.pets.dagger.modules

import abdulmanov.eduard.pets.data.database.dao.EventDao
import abdulmanov.eduard.pets.data.database.dao.PetDao
import abdulmanov.eduard.pets.data.repositories.EventsRepositoryImpl
import abdulmanov.eduard.pets.data.repositories.PetsRepositoryImpl
import abdulmanov.eduard.pets.data.sharedpreferences.PetsSharedPreferences
import abdulmanov.eduard.pets.domain.repositories.EventsRepository
import abdulmanov.eduard.pets.domain.repositories.PetsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun providePetsRepository(petDao: PetDao, petsSharedPreferences: PetsSharedPreferences): PetsRepository {
        return PetsRepositoryImpl(petDao, petsSharedPreferences)
    }

    @Singleton
    @Provides
    fun provideEventsRepository(eventDao: EventDao, petsSharedPreferences: PetsSharedPreferences): EventsRepository {
        return EventsRepositoryImpl(eventDao, petsSharedPreferences)
    }
}