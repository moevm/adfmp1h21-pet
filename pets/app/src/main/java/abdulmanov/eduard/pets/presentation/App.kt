package abdulmanov.eduard.pets.presentation

import abdulmanov.eduard.pets.dagger.components.AppComponent
import abdulmanov.eduard.pets.dagger.components.DaggerAppComponent
import android.app.Application

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}