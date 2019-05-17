package com.sc.lydianlion.ui.view.history

import com.sc.core.BaseViewModel
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.lydianlion.repository.history.HistoricalRepository
import kotlinx.coroutines.*
import timber.log.Timber

class HistoricalViewModel(private val historicalRepository: HistoricalRepository) : BaseViewModel() {

    fun showHistorical(start: String, end: String) {
        scope.launch {
            liveData.postValue(historicalRepository.showHistorical(start, end, TIME_SERIES))
            Timber.i("Value posted")
        }
    }

}