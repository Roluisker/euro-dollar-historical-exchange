package com.sc.timeline.ui

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
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
import com.sc.timeline.model.GraphLineData
import com.sc.timeline.repository.history.HistoricalRepository
import kotlinx.coroutines.launch
import lecho.lib.hellocharts.model.*
import org.joda.time.DateTime
import java.util.*

open class HistoricalViewModel(var historicalRepository: HistoricalRepository, private val context: Context) :
    BaseViewModel() {

    var liveGraph = MutableLiveData<GraphLineData>()

    lateinit var currentStartDate: String
    lateinit var currentEndDate: String

    var euroToKey: String = CoreConstants.USD
    var maxRange: Float = CoreConstants.MAX_USD_RANGE

    fun showHistorical(start: String, end: String) {

        scope.launch {
            //liveDataResponse.postValue(DataResponse.Loading(TIME_SERIES))

            val historicalData = historicalRepository.showHistorical(start, end, TIME_SERIES)

            dataToLineChartData(historicalData)
            /*
            when (historicalData!!.status) {
                SUCCESS -> {
                    dataToLineChartData(historicalData)
                    //historicalData.clearData()
                    //liveDataResponse.postValue(historicalData)
                }
                ERROR -> {

                }

            }*/

        }

    }

    fun dataToLineChartData(dataResponse: DataResponse) {

       var rateItem = dataResponse.data as TimeSeriesRemote

        val axisData: MutableList<String> = ArrayList()
        var yAxisData: MutableList<String> = ArrayList()

        rateItem.rates.rateItem.keys.toTypedArray().forEach {
            axisData.add(DateUtilities.format(SIMPLE_TIME_FORMAT_DATE, DateTime(it)))
        }

        rateItem.rates.rateItem.forEach {
            it.value[euroToKey]?.let { it1 -> yAxisData.add(it1) }
        }

        val axisValues = ArrayList<AxisValue>()

        for (i in 0 until axisData.size) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(axisData[i]))
        }

        val yAxisValues = ArrayList<PointValue>()

        for (i in 0 until yAxisData.size) {
            yAxisValues.add(
                PointValue(
                    i.toFloat(),
                    yAxisData[i].toFloat()
                ).setLabel(axisData[i] + "--" + "1 Euro -> " + yAxisData[i].toFloat() + " " + euroToKey)
            )
        }

        liveGraph.postValue(GraphLineData(axisValues, yAxisValues))

    }

}