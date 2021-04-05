package abdulmanov.eduard.pets.data.repositories

import abdulmanov.eduard.pets.data.database.dao.InterviewDao
import abdulmanov.eduard.pets.data.database.models.InterviewDbModel
import abdulmanov.eduard.pets.data.sharedpreferences.PetsSharedPreferences
import abdulmanov.eduard.pets.domain.models.interview.Interview
import abdulmanov.eduard.pets.domain.repositories.InterviewsRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate

class InterviewsRepositoryImpl(
    private val interviewDao: InterviewDao,
    private val sharedPreferences: PetsSharedPreferences
): InterviewsRepository {

    override fun getInterviewByDate(date: LocalDate): Single<Interview> {
        return interviewDao.getInterviewByDate(date.toString(), sharedPreferences.idCurrentPet)
            .map(InterviewDbModel::toDomain)
    }

    override fun getInterviews(): Single<List<Interview>> {
        return interviewDao.getInterviews(sharedPreferences.idCurrentPet)
            .map(InterviewDbModel::toDomain)
    }

    override fun createInterview(interview: Interview): Single<Interview> {
        val dbModel = InterviewDbModel.fromDomain(interview, sharedPreferences.idCurrentPet)

        return interviewDao.insertInterview(dbModel)
            .flatMap { interviewDao.getInterviewById(it.toInt()) }
            .map(InterviewDbModel::toDomain)

    }

    override fun updateInterview(interview: Interview): Single<Interview> {
        val dbModel = InterviewDbModel.fromDomain(interview)

        return interviewDao.updateInterview(dbModel)
            .andThen(interviewDao.getInterviewById(interview.id))
            .map(InterviewDbModel::toDomain)
    }

    override fun deleteInterview(interviewId: Int): Completable {
        return interviewDao.deleteById(interviewId)
    }
}