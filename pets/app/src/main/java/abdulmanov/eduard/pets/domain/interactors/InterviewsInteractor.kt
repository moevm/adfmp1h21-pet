package abdulmanov.eduard.pets.domain.interactors

import abdulmanov.eduard.pets.domain.models.interview.Interview
import abdulmanov.eduard.pets.domain.repositories.InterviewsRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate

class InterviewsInteractor(private val interviewsRepository: InterviewsRepository) {

    fun getInterviewByDate(date: LocalDate): Single<Interview>{
        return interviewsRepository.getInterviewByDate(date)
    }

    fun createInterview(interview: Interview): Single<Interview> {
        return interviewsRepository.createInterview(interview)
    }

    fun updateInterview(interview: Interview): Single<Interview> {
        return interviewsRepository.updateInterview(interview)
    }

    fun deleteInterview(interviewId: Int): Completable {
        return interviewsRepository.deleteInterview(interviewId)
    }
}