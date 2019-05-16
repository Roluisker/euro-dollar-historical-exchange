package com.sc.lydianlion.ui.view.history

import com.sc.core.BaseViewModel
import com.sc.lydianlion.repository.history.HistoricalRepository
import timber.log.Timber

class HistoricalViewModel(private val historicalRepository: HistoricalRepository) : BaseViewModel() {

    fun testInjection() {
        Timber.i("Injecto")
        historicalRepository.showHistorical("asda", "asda")
    }

}