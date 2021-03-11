package abdulmanov.eduard.pets.statistic

import abdulmanov.eduard.pets.domain.interactors.StatisticInteractor
import abdulmanov.eduard.pets.domain.models.interview.Interview
import abdulmanov.eduard.pets.domain.models.statistic.StatisticItem
import abdulmanov.eduard.pets.domain.repositories.InterviewsRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate
import java.time.YearMonth

object StatisticData {

    private val repository = object : InterviewsRepository {
        override fun getInterviewByDate(date: LocalDate): Single<Interview> {
            TODO("Not yet implemented")
        }

        override fun getInterviews(): Single<List<Interview>> {
            return Single.fromCallable {
                listOf(
                    Interview(
                        id = 1,
                        rating = 5,
                        date = "2021-03-11",
                        petId = 1
                    ),
                    Interview(
                        id = 1,
                        rating = 4,
                        date = "2021-03-12",
                        petId = 1
                    ),
                    Interview(
                        id = 1,
                        rating = 3,
                        date = "2021-03-13",
                        petId = 1
                    ),
                    Interview(
                        id = 1,
                        rating = 2,
                        date = "2021-03-14",
                        petId = 1
                    ),
                    Interview(
                        id = 1,
                        rating = 5,
                        date = "2021-03-15",
                        petId = 1
                    ),
                    Interview(
                        id = 1,
                        rating = 5,
                        date = "2021-02-11",
                        petId = 1
                    ),
                    Interview(
                        id = 1,
                        rating = 5,
                        date = "2021-01-11",
                        petId = 1
                    ),
                    Interview(
                        id = 1,
                        rating = 5,
                        date = "2020-12-11",
                        petId = 1
                    ),
                )
            }
        }

        override fun createInterview(interview: Interview): Single<Interview> {
            TODO("Not yet implemented")
        }

        override fun updateInterview(interview: Interview): Single<Interview> {
            TODO("Not yet implemented")
        }

        override fun deleteInterview(interviewId: Int): Completable {
            TODO("Not yet implemented")
        }
    }

    val interactor = StatisticInteractor(repository)

    val months = listOf(
        YearMonth.of(2021, 3),
        YearMonth.of(2021, 2),
        YearMonth.of(2021, 1),
        YearMonth.of(2020, 12)
    )

    val statisticItemsForYearMonth_2021_03 = listOf(
        StatisticItem(5, 2),
        StatisticItem(4, 1),
        StatisticItem(3, 1),
        StatisticItem(2, 1)
    )
}