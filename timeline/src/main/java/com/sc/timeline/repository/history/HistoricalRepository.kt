package com.sc.timeline.repository.history

import com.sc.core.net.NuevoDataResponse
import com.sc.core.annotation.net.FixerRequest

interface HistoricalRepository {

    suspend fun showHistorical(
        startDate: String,
        endDate: String, @FixerRequest request: String
    ): NuevoDataResponse

}