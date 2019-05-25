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

        return try {
            var result = moneyApi.getMoneyTimeSeriesByDateAsync2(startDate, endDate).await()
            timeSeriesDao.insertSeries(
                TimeSeries(
                    result.start_date,
                    result.end_date,
                    Gson().toJson(result.rates.rateItem)
                )
            )
            DataResponse.success(result, requestTag)
        } catch (error: Exception) {
            errorHandler(error, requestTag)
        }

    }

    override suspend fun errorHandler(error: Exception, @FixerRequest requestTag: String): DataResponse {

        when (error) {
            is UnknownHostException -> {
                //Timber.d(timeSeriesDao.seriesByDates("2019-03-05", "2019-03-24").id.toString())
                val timeSeriesList = timeSeriesDao.series()

                if (timeSeriesList.isNotEmpty()) {

                    val valuesFromDb =
                        Gson().fromJson<HashMap<String, HashMap<String, String>>>(
                            timeSeriesList[0].rateItemJson,
                            object : TypeToken<HashMap<String, HashMap<String, String>>>() {
                            }.type
                        )

                    Timber.d(valuesFromDb.isEmpty().toString())

                    var timeRemote = TimeSeriesRemote(true, "-", "-")

                    var timeSeries = TimeSeries()
                    timeSeries.rateItem = valuesFromDb

                    timeRemote.rates = timeSeries

                    return DataResponse.success(timeRemote, requestTag)

                } else {
                    return DataResponse.error(HISTORICAL_UNEXPECTED_ERROR, requestTag)
                }

            }
        }

        return DataResponse.error(HISTORICAL_UNEXPECTED_ERROR, requestTag)

    }

}

