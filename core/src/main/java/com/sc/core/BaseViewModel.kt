package com.sc.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sc.core.net.DataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel : ViewModel() {

    var liveDataResponse = MutableLiveData<DataResponse>()
    var liveDataErrorResponse = MutableLiveData<DataResponse>()

    val job = Job()

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() {
        ioScope()?.coroutineContext.cancelChildren()
        uiScope()?.coroutineContext.cancelChildren()
    }

    abstract fun uiScope(): CoroutineScope

    abstract fun ioScope(): CoroutineScope

}