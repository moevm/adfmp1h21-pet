package abdulmanov.eduard.pets.dagger.modules

import abdulmanov.eduard.pets.domain.interactors.EventsInteractor
import abdulmanov.eduard.pets.domain.interactors.InterviewsInteractor
import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.domain.interactors.StatisticInteractor
import abdulmanov.eduard.pets.domain.repositories.EventsRepository
import abdulmanov.eduard.pets.domain.repositories.InterviewsRepository
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

    @Singleton
    @Provides
    fun provideEventsInteractor(eventsRepository: EventsRepository): EventsInteractor {
        return EventsInteractor(eventsRepository)
    }

    @Singleton
    @Provides
    fun provideInterviewsInteractor(interviewsRepository: InterviewsRepository): InterviewsInteractor {
        return InterviewsInteractor(interviewsRepository)
    }

    @Singleton
    @Provides
    fun provideStatisticInteractor(interviewsRepository: InterviewsRepository): StatisticInteractor {
        return StatisticInteractor(interviewsRepository)
    }
}