package com.sc.timeline.repository

import com.sc.core.BaseRepository
import com.sc.core.net.DataResponse
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.api.MoneyApi
import com.sc.core.db.TimeSeriesDao
import com.sc.core.model.local.TimeSeries
import timber.log.Timber
import java.net.UnknownHostException

const val HISTORICAL_UNEXPECTED_ERROR = 1

open class HistoricalRepositoryImpl(
    var moneyApi: MoneyApi,
    val timeSeriesDao: TimeSeriesDao
) : BaseRepository(),
    HistoricalRepository {

    override suspend fun fetchHistorical(
        startDate: String,
        endDate: String,
        @FixerRequest requestTag: String
    ): DataResponse {

        return try {
            DataResponse.success(moneyApi.getMoneyTimeSeriesByDateAsync2(startDate, endDate).await(), requestTag)
        } catch (error: Exception) {
            errorHandler(error, requestTag)
        }

    }

    suspend fun errorHandler(error: Exception, @FixerRequest requestTag: String): DataResponse {

        when (error) {
            is UnknownHostException -> {
                timeSeriesDao.insertSeries(TimeSeries("2019-03-05", "2019-03-24"))
                Timber.d(timeSeriesDao.seriesByDates("2019-03-05", "2019-03-24").id.toString())
                DataResponse.error(HISTORICAL_UNEXPECTED_ERROR, requestTag)
            }
        }

        return DataResponse.error(HISTORICAL_UNEXPECTED_ERROR, requestTag)

    }

}

