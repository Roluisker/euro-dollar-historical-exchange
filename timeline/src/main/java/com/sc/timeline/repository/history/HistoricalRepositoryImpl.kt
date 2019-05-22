package com.sc.timeline.repository.history

import com.sc.core.BaseRepository
import com.sc.core.annotation.NuevoDataResponse
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.api.MoneyApi

open class HistoricalRepositoryImpl(private val moneyApi: MoneyApi) : BaseRepository(),
    HistoricalRepository {

    override suspend fun showHistorical(
        startDate: String,
        endDate: String, @FixerRequest requestTag: String
    ): NuevoDataResponse {

        return try {
            //DataResponse.Success(moneyApi.getMoneyTimeSeriesByDateAsync2(startDate, endDate).await(), requestTag)
            NuevoDataResponse.success(moneyApi.getMoneyTimeSeriesByDateAsync2(startDate, endDate).await(), requestTag)
        } catch (error: Exception) {
            //DataResponse.Error(TimeSeriesRemote(false), error, requestTag)
            NuevoDataResponse.error(-1, requestTag)
        }

    }

}

