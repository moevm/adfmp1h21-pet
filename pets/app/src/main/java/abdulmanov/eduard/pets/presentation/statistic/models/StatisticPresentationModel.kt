package abdulmanov.eduard.pets.presentation.statistic.models

import com.github.mikephil.charting.data.PieEntry

data class StatisticPresentationModel(
    val entries: List<PieEntry>,
    val colors: List<Int>
)