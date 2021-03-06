package abdulmanov.eduard.pets.presentation.time_picker

import android.os.Build
import android.widget.TimePicker
import java.util.*

@Suppress("DEPRECATION")
object TimePickerDelegate {

    fun setTime(timePicker: TimePicker, time: String?) {
        val hour: Int
        val minute: Int

        if(time != null && time.isNotEmpty()) {
            val timeSplit = time.split(":")
            hour = timeSplit[0].toInt()
            minute = timeSplit[1].toInt()
        } else {
            val calendar = Calendar.getInstance()
            hour = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = hour
            timePicker.minute = minute
        } else {
            timePicker.currentHour = hour
            timePicker.currentMinute = minute
        }
    }

    fun getTime(timePicker: TimePicker): String {
        val hour: Int
        val minute: Int

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            hour = timePicker.hour
            minute = timePicker.minute
        }else{
            hour = timePicker.currentHour
            minute = timePicker.currentMinute
        }

        return "${hour.format()}:${minute.format()}"
    }

    private fun Int.format() = if(this.toString().length > 1) "$this" else "0$this"
}