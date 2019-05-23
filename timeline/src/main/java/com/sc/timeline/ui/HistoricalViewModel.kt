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
import kotlinx.coroutines.*
import lecho.lib.hellocharts.model.*
import org.joda.time.DateTime
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Named

open class HistoricalViewModel(
    var historicalRepository: HistoricalRepository, private val context: Context,
    mainDispacher: CoroutineDispatcher = Dispatchers.Main,
    ioDispacher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    var liveGraph = MutableLiveData<GraphLineData>()

    lateinit var currentStartDate: String
    lateinit var currentEndDate: String

    var euroToKey: String = CoreConstants.USD
    var maxRange: Float = CoreConstants.MAX_USD_RANGE

    //var mainDispacher = Dispatchers.Main
    //var ioDispacher = Dispatchers.IO

    private val job = Job()

    private val uiScope = CoroutineScope(mainDispacher + job)

    val ioScope = CoroutineScope(ioDispacher + job)

    fun showHistorical(start: String, end: String) {

        uiScope.launch {

            try {

                val historicalData = ioScope.async {
                    return@async historicalRepository.showHistorical(start, end, TIME_SERIES)
                }.await()

                when (historicalData!!.status) {
                    SUCCESS -> {
                       val grap = dataToLineChartData(historicalData)
                        liveGraph.value = grap
                    }
                    ERROR -> {
                        handleError(historicalData)
                    }
                }

                Timber.d(historicalData.status)

            } catch (error: Exception) {
                Timber.d(error)
            }

        }

    }

    /*
    fun showHistorical(start: String, end: String) {

        scope.launch {
            //liveDataResponse.postValue(DataResponse.Loading(TIME_SERIES))

            val historicalData = historicalRepository.showHistorical(start, end, TIME_SERIES)

            //dataToLineChartData(historicalData)


            when (historicalData!!.status) {
                SUCCESS -> {
                    dataToLineChartData(historicalData)
                    //historicalData.clearData()
                    //liveDataResponse.postValue(historicalData)
                }
                ERROR -> {
                    handleError(historicalData)
                }

            }

        }

    }*/

    fun handleError(dataResponse: DataResponse) {

    }

    fun dataToLineChartData(dataResponse: DataResponse): GraphLineData {

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

        return GraphLineData(axisValues, yAxisValues)

        //liveGraph.value = GraphLineData(axisValues, yAxisValues)

        //liveGraph.postValue(GraphLineData(axisValues, yAxisValues))

    }

}