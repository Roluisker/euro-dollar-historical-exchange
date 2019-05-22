package com.sc.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sc.core.net.NuevoDataResponse
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    var liveDataResponse = MutableLiveData<NuevoDataResponse>()

    private val job = SupervisorJob()
    var scope = CoroutineScope(Dispatchers.Default + job)

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() = scope.coroutineContext.cancelChildren()

}