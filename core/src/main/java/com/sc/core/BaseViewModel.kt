package com.sc.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sc.core.net.DataResponse

open class BaseViewModel : ViewModel() {

    var liveDataResponse = MutableLiveData<DataResponse>()
    var liveDataErrorResponse = MutableLiveData<DataResponse>()

    //var job = SupervisorJob()
    //var scope = CoroutineScope(Dispatchers.Default + job)

    override fun onCleared() {
        super.onCleared()
        //cancelAllRequests()
    }

    //private fun cancelAllRequests() = scope.coroutineContext.cancelChildren()

}