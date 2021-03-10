package abdulmanov.eduard.pets.presentation.statistic

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.domain.interactors.StatisticInteractor
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.statistic.mapper.StatisticMapperPresentation
import abdulmanov.eduard.pets.presentation.statistic.models.StatisticPresentationModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.terrakok.cicerone.Router
import com.hadilq.liveevent.LiveEvent
import java.time.YearMonth
import javax.inject.Inject

class StatisticViewModel @Inject constructor(
    private val router: Router,
    private val statisticInteractor: StatisticInteractor,
    private val statisticMapperPresentation: StatisticMapperPresentation
) : BaseViewModel() {

    private val _months = LiveEvent<List<YearMonth>>()
    val months: LiveData<List<YearMonth>>
        get() = _months

    private val _data = MutableLiveData<StatisticPresentationModel>()
    val data: LiveData<StatisticPresentationModel>
        get() = _data

    fun onBackCommandClick() = router.exit()

    fun getMonths(){
        statisticInteractor.getMonths()
            .safeSubscribe(
                {
                    _months.value = it
                },
                {

                }
            )
    }

    fun getStatistic(month: YearMonth){
        statisticInteractor.getStatistic(month)
            .map(statisticMapperPresentation::invoke)
            .safeSubscribe { _data.value = it }
    }

}