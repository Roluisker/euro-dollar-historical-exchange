package com.sc.timeline.repository.history

import com.sc.core.annotation.net.FixerRequest
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse

interface HistoricalRepository {

    suspend fun showHistorical(
        startDate: String,
        endDate: String, @FixerRequest request: String
    ): DataResponse<TimeSeriesRemote>?

}