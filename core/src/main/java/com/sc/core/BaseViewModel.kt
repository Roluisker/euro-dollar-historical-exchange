package com.sc.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sc.core.net.DataResponse

class BaseViewModel : ViewModel() {
    val liveData = MutableLiveData<DataResponse>()
}