package com.sc.timeline.ui

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sc.core.BaseFragment
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.BasicError
import com.sc.core.net.DataResponse
import com.sc.core.ui.coreComponent
import com.sc.timeline.di.DaggerTimelineComponent
import com.sc.timeline.ui.di.HistoricalFragmentModule
import kotlinx.android.synthetic.main.historical_fragment.*
import javax.inject.Inject
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.model.Viewport
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getColor
import com.sc.core.CoreConstants.Companion.JPY
import com.sc.core.CoreConstants.Companion.MAX_USD_RANGE
import com.sc.core.CoreConstants.Companion.MAX_YEN_RANGE
import com.sc.core.CoreConstants.Companion.USD
import com.sc.core.DateUtilities
import com.sc.core.SIMPLE_TIME_FORMAT_DATE
import com.sc.timeline.R
import org.joda.time.DateTime

const val DEFAULT_HISTORICAL = 180

class HistoricalFragment : BaseFragment() {

    @Inject
    lateinit var historicalViewModel: HistoricalViewModel
    var euroToKey: String = USD
    var maxRange: Float = MAX_USD_RANGE

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        historicalViewModel.showHistorical(DateUtilities.todayMinusDays(DEFAULT_HISTORICAL), DateUtilities.today())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onSuccessResponse(data: Any?, @FixerRequest request: String?) {
        request?.let {
            when (it) {
                TIME_SERIES -> showChartLine(data as TimeSeriesRemote)
            }
        }
    }

    override fun onFailureResponse(error: BasicError?, @FixerRequest request: String?) {

    }

    private fun setListeners() {
        euroTo.setOnClickListener {
            showMoneyOptions()
        }
    }

    private fun showMoneyOptions() {
        val checkedItem = 0
        arrayOf(USD, JPY).let {

            AlertDialog.Builder(context!!).apply {
                setTitle(getString(R.string.euro_to))
                setSingleChoiceItems(
                    it, checkedItem
                ) { dialog, which ->
                    euroToKey = it[which]
                }
                setPositiveButton(getString(R.string.done)) { dialog, which ->
                    run {
                        dialog.dismiss()
                        currentMoney(euroToKey)
                        historicalViewModel.showHistorical(
                            DateUtilities.todayMinusDays(DEFAULT_HISTORICAL),
                            DateUtilities.today()
                        )
                    }
                }
                create()
                show()
            }

        }
    }

    private fun currentMoney(key: String) {
        key?.let {
            when (it) {
                USD -> {
                    euroTo.text = getString(R.string.euro_dollar)
                    maxRange = MAX_USD_RANGE
                }
                JPY -> {
                    euroTo.text = getString(R.string.euro_yen)
                    maxRange = MAX_YEN_RANGE
                }
            }
        }
    }

    private fun showChartLine(rateItem: TimeSeriesRemote) {

        val axisData: MutableList<String> = ArrayList()
        var yAxisData: MutableList<String> = ArrayList()

        rateItem.rates.rateItem.keys.toTypedArray().forEach {
            axisData.add(DateUtilities.format(SIMPLE_TIME_FORMAT_DATE, DateTime(it)))
        }

        rateItem.rates.rateItem.forEach {
            it.value[euroToKey]?.let { it1 -> yAxisData.add(it1) }
        }

        val yAxisValues = ArrayList<PointValue>()
        val axisValues = ArrayList<AxisValue>()

        val line = Line(yAxisValues)

        line.color = getColor(context!!, R.color.colorBlack)
        line.setHasLabels(true)
        line.setHasLabelsOnlyForSelected(true)

        for (i in 0 until axisData.size) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(axisData[i]))
        }

        for (i in 0 until yAxisData.size) {
            yAxisValues.add(PointValue(i.toFloat(), yAxisData[i].toFloat()).setLabel(axisData[i] + "--" + "1 Euro -> " +yAxisData[i].toFloat()+" " + euroToKey))
        }

        val lines = ArrayList<Line>()
        lines.add(line)

        val data = LineChartData()
        data.lines = lines

        val axis = Axis()
        axis.values = axisValues
        axis.textSize = 12
        axis.textColor = getColor(context!!, R.color.colorPrimary)
        data.axisXBottom = axis

        val yAxis = Axis()
        yAxis.name = ""
        yAxis.textColor = getColor(context!!, R.color.colorPrimary)
        yAxis.textSize = 16
        data.axisYLeft = yAxis

        lineChart.lineChartData = data
        val viewport = Viewport(lineChart.maximumViewport)
        viewport.top = maxRange
        lineChart.maximumViewport = viewport
        lineChart.currentViewport = viewport

    }

    private fun initDependencyInjection() =
        DaggerTimelineComponent
            .builder()
            .coreComponent(coreComponent())
            .historicalFragmentModule(HistoricalFragmentModule(this))
            .build()
            .inject(this)

    override fun mutableLiveData(): MutableLiveData<DataResponse<Any>> = historicalViewModel.liveData

    override fun fragmentLayout(): Int = R.layout.historical_fragment

}