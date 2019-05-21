package com.sc.timeline.repository.history

import com.sc.core.BaseRepository
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.api.MoneyApi
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse

open class HistoricalRepositoryImpl(private val moneyApi: MoneyApi) : BaseRepository(),
    HistoricalRepository {

    override suspend fun showHistorical(
        startDate: String,
        endDate: String, @FixerRequest requestTag: String
    ): DataResponse<TimeSeriesRemote> {

        return try {
            DataResponse.Success(moneyApi.getMoneyTimeSeriesByDateAsync(startDate, endDate).await(), requestTag)
        } catch (error: Exception) {
            DataResponse.Error(TimeSeriesRemote(false), error, requestTag)
        }

    }

}

