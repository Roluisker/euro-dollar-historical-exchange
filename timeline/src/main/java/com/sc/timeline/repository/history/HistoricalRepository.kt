package com.sc.timeline.repository.history

import com.sc.core.net.DataResponse
import com.sc.core.annotation.net.FixerRequest

interface HistoricalRepository {

    suspend fun fetchHistorical(
        startDate: String,
        endDate: String, @FixerRequest request: String
    ): DataResponse

}