package abdulmanov.eduard.pets.presentation.date_picker

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DatePickerDelegate {

    private val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")

    fun getDateAsLocalDate(date: String?): LocalDate {
        return if(date != null && date.isNotEmpty()){
            LocalDate.parse(date, dateFormatter)
        }else{
            LocalDate.now()
        }
    }

    fun getDateAsString(date: LocalDate?): String {
        return if(date != null){
            dateFormatter.format(date)
        }else{
            ""
        }
    }
}