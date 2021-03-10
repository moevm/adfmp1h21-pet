package abdulmanov.eduard.pets.presentation.statistic

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentStatisticBinding
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.pets.presentation._common.extensions.bind
import abdulmanov.eduard.pets.presentation._common.extensions.initSpinner
import abdulmanov.eduard.pets.presentation.statistic.models.StatisticPresentationModel
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import java.time.YearMonth


class StatisticFragment: BaseFragment<FragmentStatisticBinding>() {

    private val viewModel by initViewModel<StatisticViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.months.observe(viewLifecycleOwner, Observer(::setMonths))
        viewModel.data.observe(viewLifecycleOwner, Observer(::setStatistic))

        if(savedInstanceState == null){
            viewModel.getMonths()
        }
    }

    private fun initUI(){
        binding.toolbar.run {
            setTitle(R.string.statistic_toolbar_title)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
        }

        binding.chart.run {
            setUsePercentValues(true)
            description.isEnabled = false
            holeRadius = 0f
            transparentCircleRadius = 0f
            isRotationEnabled = false
            isHighlightPerTapEnabled = false
            legend.isEnabled = false
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
        }
    }

    private fun setMonths(months: List<YearMonth>){
        if(months.isNotEmpty()) {
            val monthsStr = resources.getStringArray(R.array.months_nominative)
            val items = months.map { "${monthsStr[it.monthValue - 1]} ${it.year}" }
            binding.monthTextView.initSpinner(items)
            binding.monthTextView.bind {
                val index = items.indexOf(it)
                viewModel.getStatistic(months[index])
            }
            binding.monthTextView.setText(items[0], false)
        }else{
            binding.monthTextInputLayout.isVisible = false
            binding.chart.isVisible = false
            binding.emptyData.isVisible = true
        }
    }

    private fun setStatistic(statistic: StatisticPresentationModel) {
        val dataSet = PieDataSet(statistic.entries, "")
        dataSet.colors = statistic.colors
        val data = PieData(dataSet)

        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        binding.chart.data = data
        binding.chart.invalidate()
    }

    companion object {
        fun newInstance() = StatisticFragment()
    }
}