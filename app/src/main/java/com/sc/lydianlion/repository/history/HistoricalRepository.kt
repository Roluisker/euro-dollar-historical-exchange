package com.sc.lydianlion.repository.history

interface HistoricalRepository {
    fun showHistorical(startDate: String, endDate: String)
}