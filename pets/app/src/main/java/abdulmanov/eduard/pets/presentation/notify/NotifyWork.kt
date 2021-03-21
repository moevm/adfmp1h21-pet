package abdulmanov.eduard.pets.presentation.notify;

import android.content.Context;

import androidx.work.WorkerParameters;

import abdulmanov.eduard.pets.domain.interactors.EventsInteractor;
import abdulmanov.eduard.pets.presentation._common.worker.ChildWorkerFactory
import androidx.work.ListenableWorker
import androidx.work.Worker
import javax.inject.Inject

class NotifyWork(
    private val context:Context,
    params:WorkerParameters,
    private val eventsInteractor:EventsInteractor
): Worker(context, params) {

    override fun doWork(): Result {
        return Result.success()
    }

    class Factory @Inject constructor(
        private val eventsInteractor: EventsInteractor
    ): ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return NotifyWork(appContext, params, eventsInteractor)
        }
    }
}
