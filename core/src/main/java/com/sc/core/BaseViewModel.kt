package com.sc.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sc.core.net.DataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    val liveData = MutableLiveData<DataResponse<Any>>()

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    protected val scope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() = coroutineContext.cancel()

}