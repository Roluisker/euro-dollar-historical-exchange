package com.sc.lydianlion.repository.history

import com.sc.core.model.remote.TimeSeriesRemote

interface HistoricalRepository {
    suspend fun showHistorical(startDate: String, endDate: String): TimeSeriesRemote?
}