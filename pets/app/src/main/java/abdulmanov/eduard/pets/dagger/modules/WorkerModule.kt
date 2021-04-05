package abdulmanov.eduard.pets.dagger.modules

import abdulmanov.eduard.pets.dagger.annotations.WorkerKey
import abdulmanov.eduard.pets.presentation._common.worker.ChildWorkerFactory
import abdulmanov.eduard.pets.presentation._common.worker.NotifyWorkerFactory
import abdulmanov.eduard.pets.presentation.notify.NotifyWork
import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkerModule {

    @Binds
    abstract fun bindWorkerFactory(factory: NotifyWorkerFactory): WorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(NotifyWork::class)
    abstract fun provideNotifyWork(factory: NotifyWork.Factory): ChildWorkerFactory
}