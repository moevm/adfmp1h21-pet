package abdulmanov.eduard.pets.dagger.components

import abdulmanov.eduard.pets.dagger.modules.*
import abdulmanov.eduard.pets.presentation.calendar.CalendarFragment
import abdulmanov.eduard.pets.presentation.main.MainActivity
import abdulmanov.eduard.pets.presentation.options.OptionsFragment
import abdulmanov.eduard.pets.presentation.pet.PetFragment
import android.content.Context
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
        ViewModelModule::class
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
}