package com.sc.timeline.ui

import android.content.Context
import androidx.core.content.ContextCompat
import com.sc.core.BaseViewModel
import com.sc.core.CoreConstants
import com.sc.core.CoreConstants.Companion.EMPTY
import com.sc.core.DateUtilities
import com.sc.core.SIMPLE_TIME_FORMAT_DATE
import com.sc.core.annotation.net.ERROR
import com.sc.core.annotation.net.SUCCESS
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.timeline.R
import com.sc.timeline.repository.history.HistoricalRepository
import kotlinx.coroutines.launch
import lecho.lib.hellocharts.model.*
import org.joda.time.DateTime
import java.util.*

open class HistoricalViewModel(var historicalRepository: HistoricalRepository, private val context: Context) :
    BaseViewModel() {

    private val axisData: MutableList<String> = ArrayList()
    private var yAxisData: MutableList<String> = ArrayList()

    lateinit var currentStartDate: String
    lateinit var currentEndDate: String

    var euroToKey: String = CoreConstants.USD
    var maxRange: Float = CoreConstants.MAX_USD_RANGE

    fun showHistorical(start: String, end: String) {

        scope.launch {
            liveData.postValue(DataResponse.Loading(TIME_SERIES))

            val historicalData = historicalRepository.showHistorical(start, end, TIME_SERIES)

            when (historicalData!!.status) {
                SUCCESS -> {

                }
                ERROR -> {

                }

            }

            /*
            when (val historicalData = historicalRepository.showHistorical(start, end, TIME_SERIES)) {
                is DataResponse.Success -> {
                    liveData.postValue(DataResponse.Success(dataToLineChartData(historicalData.data), TIME_SERIES))
                }
                is DataResponse.Error -> {
                    liveData.postValue(historicalData)
                }
            }*/

        }

    }

    private fun dataToLineChartData(rateItem: TimeSeriesRemote): LineChartData {

        val yAxisValues = ArrayList<PointValue>()
        val axisValues = ArrayList<AxisValue>()
        val line = Line(yAxisValues)
        val lines = ArrayList<Line>()
        val data = LineChartData()

        axisData.clear()
        yAxisData.clear()

        rateItem.rates.rateItem.keys.toTypedArray().forEach {
            axisData.add(DateUtilities.format(SIMPLE_TIME_FORMAT_DATE, DateTime(it)))
        }

        rateItem.rates.rateItem.forEach {
            it.value[euroToKey]?.let { it1 -> yAxisData.add(it1) }
        }

        line.apply {
            color = ContextCompat.getColor(context!!, R.color.colorBlack)
            setHasLabels(true)
            setHasLabelsOnlyForSelected(true)
        }

        for (i in 0 until axisData.size) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(axisData[i]))
        }

        for (i in 0 until yAxisData.size) {
            yAxisValues.add(
                PointValue(
                    i.toFloat(),
                    yAxisData[i].toFloat()
                ).setLabel(axisData[i] + "--" + "1 Euro -> " + yAxisData[i].toFloat() + " " + euroToKey)
            )
        }

        lines.add(line)

        data.lines = lines

        val axis = Axis().apply {
            values = axisValues
            textSize = 12
            textColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
        }

        data.axisXBottom = axis

        val yAxis = Axis().apply {
            name = EMPTY
            textColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
            textSize = 16
        }

        data.axisYLeft = yAxis

        return data

    }

}