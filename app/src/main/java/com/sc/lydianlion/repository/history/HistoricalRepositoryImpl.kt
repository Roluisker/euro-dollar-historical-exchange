package com.sc.lydianlion.repository.history

import com.sc.core.api.MoneyApi
import timber.log.Timber

class HistoricalRepositoryImpl(private val moneyApi: MoneyApi) : HistoricalRepository {

    override fun showHistorical(startDate: String, endDate: String) {
        Timber.i("showHistorical")
    }

}

