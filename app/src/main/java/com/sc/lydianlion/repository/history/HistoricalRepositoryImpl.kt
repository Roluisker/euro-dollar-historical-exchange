package com.sc.lydianlion.repository.history

import com.sc.core.BaseRepository
import com.sc.core.api.MoneyApi
import com.sc.core.model.remote.TimeSeriesRemote
import timber.log.Timber

class HistoricalRepositoryImpl(private val moneyApi: MoneyApi) : BaseRepository(), HistoricalRepository {

    override suspend fun showHistorical(startDate: String, endDate: String): TimeSeriesRemote? {
        Timber.i("showHistorical")

        return safeApiCall(
            call = { moneyApi.getMoneyTimeSeriesByDate(startDate, endDate).await() },
            errorMessage = "Error Fetching"
        )

    }

}

