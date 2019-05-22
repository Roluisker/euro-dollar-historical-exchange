package com.sc.timeline.repository.history

import com.sc.core.BaseRepository
import com.sc.core.net.DataResponse
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.api.MoneyApi

open class HistoricalRepositoryImpl(private val moneyApi: MoneyApi) : BaseRepository(),
    HistoricalRepository {

    override suspend fun showHistorical(
        startDate: String,
        endDate: String, @FixerRequest requestTag: String
    ): DataResponse {

        return try {
            DataResponse.success(moneyApi.getMoneyTimeSeriesByDateAsync2(startDate, endDate).await(), requestTag)
        } catch (error: Exception) {
            DataResponse.error(-1, requestTag)
        }

    }

}

