package abdulmanov.eduard.pets.domain.interactors

import abdulmanov.eduard.pets.domain.models.statistic.StatisticItem
import abdulmanov.eduard.pets.domain.repositories.InterviewsRepository
import com.kizitonwose.calendarview.utils.yearMonth
import io.reactivex.Single
import java.time.LocalDate
import java.time.YearMonth

class StatisticInteractor(private val interviewsRepository: InterviewsRepository) {

    fun getMonths(): Single<List<YearMonth>>{
        return interviewsRepository.getInterviews()
            .map {
                it.groupBy { interview -> LocalDate.parse(interview.date).yearMonth }.keys.toList()
            }
    }

    fun getStatistic(month: YearMonth): Single<List<StatisticItem>> {
        return interviewsRepository.getInterviews()
            .map {
                it.groupBy { interview -> LocalDate.parse(interview.date).yearMonth }
                    .getValue(month)
                    .groupBy { group -> group.rating }
                    .map { group -> StatisticItem(group.key, group.value.size) }
            }
    }
}