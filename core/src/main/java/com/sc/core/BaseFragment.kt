package com.sc.core

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.sc.core.net.BasicError
import androidx.lifecycle.Observer
import com.sc.core.annotation.NuevoDataResponse

abstract class BaseFragment : Fragment() {

    private lateinit var process: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!::process.isInitialized) {
            createProgressDialog()
        }
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
        mutableLiveData().observe(this, Observer<NuevoDataResponse> {
            consumeResponse(it)
        })
    }

    private fun consumeResponse(response: NuevoDataResponse) {

        onSuccessResponse(response.data, response.request)

        /*
        when (response) {
            is DataResponse.Loading -> {
                switchProgressDialog(true)
            }
            is DataResponse.Success -> {
                onSuccessResponse(response.data, response.requestTag)
                switchProgressDialog(false)
            }
            is DataResponse.Error -> {
                switchProgressDialog(false)
                onFailureResponse(BasicError(1, response.exception, ""), response.requestTag)
            }
        }*/

    }

    private fun createProgressDialog() {
        try {

            activity?.let {
                process = ProgressDialog(it)
                process.setMessage(getString(R.string.loading))
                process.setCancelable(false)
                process.isIndeterminate = true
            }

        } catch (e: Exception) {

        }
    }

    public fun switchProgressDialog(show: Boolean) {
        try {

            process?.let {
                if (show && !it.isShowing) {
                    it.show()
                } else if (!show && it.isShowing) {
                    it.hide()
                }
            }
        } catch (e: Exception) {

        }
    }

    abstract fun onSuccessResponse(data: Any?, request: String?)

    abstract fun onFailureResponse(error: BasicError?, request: String?)

    @LayoutRes
    abstract fun fragmentLayout(): Int

    abstract fun mutableLiveData(): MutableLiveData<NuevoDataResponse>

}