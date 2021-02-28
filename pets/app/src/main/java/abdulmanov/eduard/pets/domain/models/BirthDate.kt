package abdulmanov.eduard.pets.domain.models

import java.text.DateFormatSymbols
import java.time.LocalDate
import java.time.YearMonth

data class BirthDate(
    val month: String,
    val year: String
){

    override fun toString(): String {
        return "$month,$year"
    }

    companion object {

        fun fromString(str: String): BirthDate {
            return str.split(",").run {
                BirthDate(this[0], this[1])
            }
        }

        fun getYears(): List<String> {
            val dateNow = LocalDate.now()
            val startDate = dateNow.minusYears(20)

            return (startDate.year..dateNow.year).map { it.toString() }
        }

        fun getMonths(): List<String> {
            return DateFormatSymbols().months.toList()
        }
    }
}