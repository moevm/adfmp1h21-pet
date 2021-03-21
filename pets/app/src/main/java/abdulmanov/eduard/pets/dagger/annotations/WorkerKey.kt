package abdulmanov.eduard.pets.dagger.annotations

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerKey(val value: KClass<out ListenableWorker>)
