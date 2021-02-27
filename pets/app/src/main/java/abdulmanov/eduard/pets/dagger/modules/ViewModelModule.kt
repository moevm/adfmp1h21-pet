package abdulmanov.eduard.pets.dagger.modules

import abdulmanov.eduard.pets.dagger.annotations.ViewModelKey
import abdulmanov.eduard.pets.presentation._common.viewmodel.ViewModelFactory
import abdulmanov.eduard.pets.presentation.main.MainViewModel
import abdulmanov.eduard.pets.presentation.pet.PetViewModel
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
}