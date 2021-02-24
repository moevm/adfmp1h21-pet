package abdulmanov.eduard.pets.dagger.modules

import abdulmanov.eduard.pets.presentation._common.viewmodel.ViewModelFactory
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}