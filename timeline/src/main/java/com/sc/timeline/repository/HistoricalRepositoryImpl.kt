package com.sc.timeline.repository

import com.sc.core.BaseRepository
import com.sc.core.net.DataResponse
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.api.MoneyApi
import com.sc.core.db.TimeSeriesDao

const val HISTORICAL_UNEXPECTED_ERROR = 1

// cambio avar api
open class HistoricalRepositoryImpl(
    var moneyApi: MoneyApi,
    val timeSeriesDao: TimeSeriesDao
) : BaseRepository(),
    HistoricalRepository {

    override suspend fun fetchHistorical(
        startDate: String,
        endDate: String, @FixerRequest requestTag: String
    ): DataResponse {

        return try {
            DataResponse.success(moneyApi.getMoneyTimeSeriesByDateAsync2(startDate, endDate).await(), requestTag)
        } catch (error: Exception) {
            DataResponse.error(HISTORICAL_UNEXPECTED_ERROR, requestTag)
        }

    }

}

