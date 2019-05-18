package com.sc.lydianlion.repository.history

import com.sc.lydianlion.core.annotation.net.FixerRequest
import com.sc.lydianlion.core.model.remote.TimeSeriesRemote
import com.sc.lydianlion.core.net.DataResponse

interface HistoricalRepository {

    suspend fun showHistorical(
        startDate: String,
        endDate: String, @FixerRequest request: String
    ): DataResponse<TimeSeriesRemote>?

}