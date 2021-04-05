package abdulmanov.eduard.pets.statistic

import org.junit.Test
import java.time.YearMonth


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StatisticUnitTest {

    @Test
    fun statisticGetMonthsTest() {
        StatisticData.interactor.getMonths()
            .test()
            .assertValue { it == StatisticData.months }
    }

    @Test
    fun statisticGetTest() {
        StatisticData.interactor.getStatistic(YearMonth.of(2021,3))
            .test()
            .assertValue { it == StatisticData.statisticItemsForYearMonth_2021_03 }
    }
}