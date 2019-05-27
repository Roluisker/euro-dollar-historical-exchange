package com.sc.timeline.ui

import androidx.lifecycle.MutableLiveData
import com.sc.core.BaseViewModel
import com.sc.core.CoreConstants
import com.sc.core.DateUtilities
import com.sc.core.SIMPLE_TIME_FORMAT_DATE
import com.sc.core.annotation.net.ERROR
import com.sc.core.annotation.net.SUCCESS
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse

import com.sc.timeline.model.GraphLineData
import com.sc.timeline.repository.HistoricalRepository
import kotlinx.coroutines.*
import lecho.lib.hellocharts.model.*
import org.joda.time.DateTime
import java.util.*

const val HISTORICAL_VIEW_MODEL_UNEXPECTED_ERROR = 2

class HistoricalViewModel(
    private var historicalRepository: HistoricalRepository,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    var liveGraph = MutableLiveData<GraphLineData>()

    private val uiScope = CoroutineScope(mainDispatcher + job)

    private val ioScope = CoroutineScope(ioDispatcher + job)

    fun showHistorical(start: String, end: String): Boolean {

        var isFetchHistoricalLaunched = false

        try {

            uiScope.launch {

                try {

                    liveDataResponse.value = DataResponse.loading(TIME_SERIES)

                    val currentResponse = ioScope.async {
                        return@async historicalRepository.fetchHistorical(start, end, TIME_SERIES)
                    }.await()

                    when (currentResponse.status) {
                        SUCCESS -> {
                            liveGraph.value = dataToLineChartData(currentResponse)
                            liveDataResponse.value = currentResponse
                        }
                        ERROR -> {
                            liveDataErrorResponse.value = currentResponse
                        }
                    }

                } catch (error: Exception) {
                    liveDataErrorResponse.value =
                        DataResponse.error(HISTORICAL_VIEW_MODEL_UNEXPECTED_ERROR, TIME_SERIES)
                    isFetchHistoricalLaunched = false
                }

            }

        } catch (error: Exception) {
            liveDataErrorResponse.value = DataResponse.error(HISTORICAL_VIEW_MODEL_UNEXPECTED_ERROR, TIME_SERIES)
            isFetchHistoricalLaunched = false
        }

        return isFetchHistoricalLaunched

    }

    private fun dataToLineChartData(dataResponse: DataResponse): GraphLineData {

        var timeSeriesRemote = dataResponse.data as TimeSeriesRemote

        val datesAndValues = TreeMap(timeSeriesRemote.rates.rateItem)

        val axisData: MutableList<String> = ArrayList()
        var yAxisData: MutableList<String> = ArrayList()

        val axisValues = ArrayList<AxisValue>()

        datesAndValues.forEach {

            axisData.add(
                DateUtilities.format(
                    SIMPLE_TIME_FORMAT_DATE,
                    DateTime(it.key)
                )
            )

            it.value[CoreConstants.USD]?.let { it1 -> yAxisData.add(it1) }

        }

        for (i in 0 until axisData.size) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(axisData[i]))
        }

        val yAxisValues = ArrayList<PointValue>()

        for (i in 0 until yAxisData.size) {
            yAxisValues.add(
                PointValue(
                    i.toFloat(),
                    yAxisData[i].toFloat()
                ).setLabel(axisData[i] + "--" + "1 Euro -> " + yAxisData[i].toFloat() + " " + CoreConstants.USD)
            )
        }

        return GraphLineData(axisValues, yAxisValues)

    }

    override fun uiScope(): CoroutineScope = uiScope

    override fun ioScope(): CoroutineScope = ioScope

}