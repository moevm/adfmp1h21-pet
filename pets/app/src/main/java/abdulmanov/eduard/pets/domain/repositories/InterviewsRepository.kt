package abdulmanov.eduard.pets.domain.repositories

import abdulmanov.eduard.pets.domain.models.interview.Interview
import io.reactivex.Completable
import io.reactivex.Single

interface InterviewsRepository {

    fun getInterviews(): Single<List<Interview>>

    fun getInterviewById(id: Int): Single<Interview>

    fun createInterview(interview: Interview): Completable

    fun updateInterview(interview: Interview): Completable

    fun deleteInterview(interviewId: Int): Completable
}