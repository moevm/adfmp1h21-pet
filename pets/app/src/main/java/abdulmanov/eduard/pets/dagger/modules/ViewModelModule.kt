package abdulmanov.eduard.pets.dagger.modules

import abdulmanov.eduard.pets.dagger.annotations.ViewModelKey
import abdulmanov.eduard.pets.presentation._common.viewmodel.ViewModelFactory
import abdulmanov.eduard.pets.presentation.calendar.CalendarViewModel
import abdulmanov.eduard.pets.presentation.calendar.dialogs.edit_event.EditEventViewModel
import abdulmanov.eduard.pets.presentation.options.dialogs.change_pet.ChangePetViewModel
import abdulmanov.eduard.pets.presentation.event.EventViewModel
import abdulmanov.eduard.pets.presentation.interview.InterviewViewModel
import abdulmanov.eduard.pets.presentation.main.MainViewModel
import abdulmanov.eduard.pets.presentation.options.OptionsViewModel
import abdulmanov.eduard.pets.presentation.pet.PetViewModel
import abdulmanov.eduard.pets.presentation.statistic.StatisticViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PetViewModel::class)
    abstract fun bindPetViewModel(viewModel: PetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    abstract fun bindCalendarViewModel(viewModel: CalendarViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OptionsViewModel::class)
    abstract fun bindOptionsViewModel(viewModel: OptionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangePetViewModel::class)
    abstract fun bindChangePetViewModel(viewModel: ChangePetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventViewModel::class)
    abstract fun bindEventViewModel(viewModel: EventViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditEventViewModel::class)
    abstract fun bindEditEventViewModel(viewModel: EditEventViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InterviewViewModel::class)
    abstract fun bindInterviewViewModel(viewModel: InterviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticViewModel::class)
    abstract fun bindStatisticViewModel(viewModel: StatisticViewModel): ViewModel
}