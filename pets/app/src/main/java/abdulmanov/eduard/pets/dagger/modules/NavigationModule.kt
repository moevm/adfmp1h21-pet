package abdulmanov.eduard.pets.dagger.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun provideRouter(): Router {
        return cicerone.router
    }

    @Singleton
    @Provides
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}