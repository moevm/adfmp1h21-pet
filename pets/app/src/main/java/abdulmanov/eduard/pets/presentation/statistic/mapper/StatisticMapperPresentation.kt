package abdulmanov.eduard.pets.presentation.statistic.mapper

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.domain.models.statistic.StatisticItem
import abdulmanov.eduard.pets.presentation._common.StringProvider
import abdulmanov.eduard.pets.presentation.statistic.models.StatisticPresentationModel
import android.graphics.Color
import com.github.mikephil.charting.data.PieEntry
import javax.inject.Inject

class StatisticMapperPresentation @Inject constructor(
    private val stringProvider: StringProvider
): (List<StatisticItem>) -> StatisticPresentationModel {

    override fun invoke(statisticItems: List<StatisticItem>): StatisticPresentationModel {
        val moods = stringProvider.getArrayString(R.array.moods)

        val entries = statisticItems
            .sortedBy { it.rating }
            .map { PieEntry(it.count.toFloat(), moods[it.rating-1]) }

        val colors = statisticItems
            .sortedBy { it.rating }
            .map {
                when(it.rating){
                    1 -> Color.parseColor("#cdcdcd")
                    2 -> Color.parseColor("#f1e3cd")
                    3 -> Color.parseColor("#f4cbe5")
                    4 -> Color.parseColor("#fff2b0")
                    5 -> Color.parseColor("#e7f5ca")
                    else -> Color.parseColor("#e7f5ca")
                }
            }

        return StatisticPresentationModel(entries, colors)
    }
}