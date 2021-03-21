package abdulmanov.eduard.pets.presentation.notify;

import abdulmanov.eduard.pets.R
import android.content.Context;

import abdulmanov.eduard.pets.domain.interactors.EventsInteractor;
import abdulmanov.eduard.pets.domain.models.event.RepeatMode
import abdulmanov.eduard.pets.presentation._common.worker.ChildWorkerFactory
import abdulmanov.eduard.pets.presentation.main.MainActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class NotifyWork(
    private val context:Context,
    params:WorkerParameters,
    private val eventsInteractor:EventsInteractor
): Worker(context, params) {

    override fun doWork(): Result {
        val event = eventsInteractor.getEventById(inputData.getInt(ARG_EVENT_ID, -1)).blockingGet()

        val isPeriodicity = event.repeatMode == RepeatMode.REPEAT_EVERY_DAY
            || event.repeatMode == RepeatMode.REPEAT_EVERY_WEEK
            || event.repeatMode == RepeatMode.REPEAT_EVERY_MONTH

        if(isPeriodicity) {
            val currentCalendar = Calendar.getInstance()
            val notifyCalendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, event.time.split(":")[0].toInt())
                set(Calendar.MINUTE, event.time.split(":")[1].toInt())
            }

            when (event.repeatMode) {
                RepeatMode.REPEAT_EVERY_DAY -> {
                    notifyCalendar.add(Calendar.DAY_OF_MONTH, 1)
                }
                RepeatMode.REPEAT_EVERY_WEEK -> {
                    notifyCalendar.add(Calendar.WEEK_OF_MONTH, 1)
                }
                RepeatMode.REPEAT_EVERY_MONTH -> {
                    notifyCalendar.add(Calendar.MONTH, 1)
                }
                else -> {}
            }

            val delay = notifyCalendar.timeInMillis - currentCalendar.timeInMillis

            val dailyWorkRequest = OneTimeWorkRequest.Builder(NotifyWork::class.java)
                .setInputData(newData(event.id))
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .addTag(event.id.toString())
                .build()

            WorkManager.getInstance(context).enqueue(dailyWorkRequest)
        }

        if(event.isNotification && LocalDate.now().toString() !in event.doneDates) {
            sendNotification(event.name)
        }

        return Result.success()
    }

    private fun sendNotification(message: String){
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = context.getString(R.string.notification_channel_id)
        val channelName = context.getString(R.string.notification_channel_name)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_pets)
            .setColor(ContextCompat.getColor(context, R.color.colorAccent))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setBigContentTitle(context.getString(R.string.event_name))
                    .bigText(message)
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(Random.nextInt(0, 1000), notificationBuilder.build())
    }

    class Factory @Inject constructor(
        private val eventsInteractor: EventsInteractor
    ): ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return NotifyWork(appContext, params, eventsInteractor)
        }
    }

    companion object {
        private const val ARG_EVENT_ID = "event_id"

        fun newData(eventId: Int): Data {
            return Data.Builder()
                .putInt(ARG_EVENT_ID, eventId)
                .build()
        }
    }
}
