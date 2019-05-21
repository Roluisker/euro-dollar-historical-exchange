package com.sc.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sc.core.net.DataResponse
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    val liveData = MutableLiveData<DataResponse<Any>>()

    //val liveData = MutableLiveData<Any>()

    /*
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    protected val scope = CoroutineScope(coroutineContext)*/

    val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Default + job)

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() = scope.coroutineContext.cancelChildren()

}