package com.sc.lydianlion.ui.view.history

import androidx.lifecycle.MutableLiveData
import com.sc.core.BaseViewModel
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.lydianlion.repository.history.HistoricalRepository
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class HistoricalViewModel(private val historicalRepository: HistoricalRepository) : BaseViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    val moneyHistory = MutableLiveData<TimeSeriesRemote>()

    fun showHistorical(start: String, end: String) {

        scope.launch {
            val moneySeries = historicalRepository.showHistorical(start, end)
            moneyHistory.postValue(moneySeries)
            Timber.i("Value posted")
        }

    }

    fun cancelAllRequests() = coroutineContext.cancel()

}