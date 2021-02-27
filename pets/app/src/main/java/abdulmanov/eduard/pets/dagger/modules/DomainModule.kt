package abdulmanov.eduard.pets.dagger.modules

import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.domain.repositories.PetsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun providePetsInteractor(petsRepository: PetsRepository): PetsInteractor {
        return PetsInteractor(petsRepository)
    }
}