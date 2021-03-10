package abdulmanov.eduard.pets.domain.repositories

import abdulmanov.eduard.pets.domain.models.interview.Interview
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate

interface InterviewsRepository {

    fun getInterviewByDate(date: LocalDate): Single<Interview>

    fun getInterviews(): Single<List<Interview>>

    fun getInterviewById(id: Int): Single<Interview>

    fun createInterview(interview: Interview): Single<Interview>

    fun updateInterview(interview: Interview): Single<Interview>

    fun deleteInterview(interviewId: Int): Completable
}