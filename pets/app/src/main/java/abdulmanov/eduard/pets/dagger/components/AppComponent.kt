package abdulmanov.eduard.pets.dagger.components

import abdulmanov.eduard.pets.dagger.modules.*
import abdulmanov.eduard.pets.presentation.calendar.CalendarFragment
import abdulmanov.eduard.pets.presentation.calendar.dialogs.edit_event.EditEventBottomSheetDialog
import abdulmanov.eduard.pets.presentation.main.MainActivity
import abdulmanov.eduard.pets.presentation.options.OptionsFragment
import abdulmanov.eduard.pets.presentation.options.dialogs.change_pet.ChangePetBottomSheetDialog
import abdulmanov.eduard.pets.presentation.event.EventFragment
import abdulmanov.eduard.pets.presentation.interview.InterviewFragment
import abdulmanov.eduard.pets.presentation.pet.PetFragment
import abdulmanov.eduard.pets.presentation.statistic.StatisticFragment
import android.content.Context
import androidx.work.WorkerFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        SharedPreferencesModule::class,
        DataModule::class,
        DomainModule::class,
        NavigationModule::class,
        ViewModelModule::class,
        WorkerModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(petFragment: PetFragment)

    fun inject(calendarFragment: CalendarFragment)

    fun inject(optionsFragment: OptionsFragment)

    fun inject(changePetBottomSheetDialog: ChangePetBottomSheetDialog)

    fun inject(eventFragment: EventFragment)

    fun inject(editEventBottomSheetDialog: EditEventBottomSheetDialog)

    fun inject(interviewFragment: InterviewFragment)

    fun inject(statisticFragment: StatisticFragment)

    fun getWorkerFactory(): WorkerFactory
}