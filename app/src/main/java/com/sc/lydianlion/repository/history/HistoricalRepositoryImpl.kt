package com.sc.lydianlion.repository.history

import com.sc.core.BaseRepository
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.api.MoneyApi
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import timber.log.Timber

class HistoricalRepositoryImpl(private val moneyApi: MoneyApi) : BaseRepository(), HistoricalRepository {

    override suspend fun showHistorical(
        startDate: String,
        endDate: String, @FixerRequest requestTag: String
    ): DataResponse<TimeSeriesRemote>? {
        Timber.i("showHistorical")

        val r = safeApiCall(
            call = {
                moneyApi.getMoneyTimeSeriesByDateAsync(startDate, endDate).await()
            },
            errorMessage = "Error Fetching",
            requestTag = requestTag
        )

        when (r) {
            is DataResponse.Success ->
                r.data
            is DataResponse.Error -> {
                r.exception
            }
        }

        return r

    }

}

