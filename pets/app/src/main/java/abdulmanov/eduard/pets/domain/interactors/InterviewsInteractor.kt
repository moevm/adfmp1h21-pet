package abdulmanov.eduard.pets.domain.interactors

import abdulmanov.eduard.pets.domain.models.interview.Interview
import abdulmanov.eduard.pets.domain.repositories.InterviewsRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate

class InterviewsInteractor(private val interviewsRepository: InterviewsRepository) {

    fun getInterviewById(id: Int): Single<Interview>{
        return interviewsRepository.getInterviewById(id)
    }

    fun createInterview(interview: Interview): Completable {
        return interviewsRepository.createInterview(interview)
    }

    fun updateInterview(interview: Interview): Completable {
        return interviewsRepository.updateInterview(interview)
    }

    fun deleteInterview(interviewId: Int): Completable {
        return interviewsRepository.deleteInterview(interviewId)
    }

    fun getInterviewsForSelectedDate(date: LocalDate): Single<List<Interview>>{
        return interviewsRepository.getInterviews()
//            .map { interviews ->
//                interviews.filter {
//                    when(it.repeatMode){
//                        RepeatMode.DO_NOT_REPEAT -> {
//                            it.date == date.toString()
//                        }
//                        RepeatMode.REPEAT_EVERY_DAY -> {
//                            LocalDate.parse(it.date).isBefore(date) || it.date == date.toString()
//                        }
//                        RepeatMode.REPEAT_EVERY_WEEK -> {
//                            val dateEvent = LocalDate.parse(it.date)
//                            date.dayOfWeek.value == dateEvent.dayOfWeek.value
//                        }
//                        RepeatMode.REPEAT_EVERY_MONTH -> {
//                            //TODO корнер кейсы
//                            val dateEvent = LocalDate.parse(it.date)
//                            date.dayOfMonth == dateEvent.dayOfMonth
//                        }
//                    }
//                }
//            }
    }

//    fun finishInterviewsForSelectedDate(interview: Interview, isDone: Boolean, date: LocalDate): Completable {
//        val updatedDoneDates = interview.doneDates.toMutableList().apply {
//            if(isDone) add(date.toString()) else remove(date.toString())
//        }
//        return interviewsRepository.updateEvent(event.copy(doneDates = updatedDoneDates))
//    }
}