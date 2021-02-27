package abdulmanov.eduard.pets.dagger.modules

import abdulmanov.eduard.pets.data.sharedpreferences.PetsSharedPreferences
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun providePetsSharedPreferences(context: Context): PetsSharedPreferences {
        return PetsSharedPreferences(context)
    }
}