package com.sc.timeline.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sc.core.BaseRepository
import com.sc.core.net.DataResponse
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.api.MoneyApi
import com.sc.core.db.TimeSeriesDao
import com.sc.core.model.local.TimeSeries
import com.sc.core.model.remote.TimeSeriesRemote
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

        try {

            var timeSeriesRemote = moneyApi.getMoneyTimeSeriesByDateAsync2(startDate, endDate).await()
            storeInLocal(timeSeriesRemote)
            return DataResponse.success(timeSeriesRemote, requestTag)
        } catch (error: Exception) {
            return errorHandler(error, requestTag)
        }

    }

    suspend fun storeInLocal(timeSeriesRemote: TimeSeriesRemote) {

        try {

            timeSeriesDao.insertSeries(
                TimeSeries(
                    timeSeriesRemote.start_date,
                    timeSeriesRemote.end_date,
                    Gson().toJson(timeSeriesRemote.rates.rateItem)
                )
            )
        } catch (error: Exception) {
            Timber.e("This error could be sent to crashlytics or something similar")
        }

    }

    override suspend fun errorHandler(error: Exception, @FixerRequest requestTag: String): DataResponse {

        try {

            when (error) {
                is UnknownHostException -> {

                    val timeSeriesList = timeSeriesDao.series()

                    if (timeSeriesList.isNotEmpty()) {

                        val valuesFromDb =
                            Gson().fromJson<HashMap<String, HashMap<String, String>>>(
                                timeSeriesList[0].rateItemJson,
                                object : TypeToken<HashMap<String, HashMap<String, String>>>() {
                                }.type
                            )

                        var timeRemote = TimeSeriesRemote(true, "-", "-")

                        var timeSeries = TimeSeries()
                        timeSeries.rateItem = valuesFromDb
                        timeRemote.rates = timeSeries

                        return DataResponse.successFromLocal(timeRemote, requestTag)

                    } else {
                        return DataResponse.error(HISTORICAL_UNEXPECTED_ERROR, requestTag)
                    }

                }
            }

            return DataResponse.error(HISTORICAL_UNEXPECTED_ERROR, requestTag)

        } catch (error: Exception) {
            return DataResponse.error(HISTORICAL_UNEXPECTED_ERROR, requestTag)
        }

    }

}

