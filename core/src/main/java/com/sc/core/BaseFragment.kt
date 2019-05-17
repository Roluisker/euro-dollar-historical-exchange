package com.sc.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.sc.core.net.BasicError
import com.sc.core.net.DataResponse
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(fragmentLayout(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModelToConsumeResponse()
    }

    private fun subscribeViewModelToConsumeResponse() {
        mutableLiveData().observe(this, Observer<DataResponse<Any>> {
            consumeResponse(it)
        })
    }

    private fun consumeResponse(response: DataResponse<Any>) {

        when (response) {
            is DataResponse.Success ->
                onSuccessResponse(response.data, response.requestTag)
            is DataResponse.Error -> {
                onFailureResponse(BasicError(1, response.exception, ""), response.requestTag)
            }
        }

    }

    abstract fun onSuccessResponse(data: Any?, request: String?)

    abstract fun onFailureResponse(error: BasicError?, request: String?)

    @LayoutRes
    abstract fun fragmentLayout(): Int

    abstract fun mutableLiveData(): LiveData<DataResponse<Any>>

}
