package com.sc.core

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.sc.core.net.ViewBasicError
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sc.core.CoreConstants.Companion.NOT_NETWORK_MODE
import com.sc.core.annotation.net.LOADING
import com.sc.core.annotation.net.SUCCESS
import com.sc.core.net.DataResponse
import com.sc.core.ui.showSnackbar

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
        subscribeViewModelToErrorHandler()
    }

    private fun subscribeViewModelToConsumeResponse() {
        dataResponseLiveData()?.observe(this, Observer<DataResponse> {
            when {
                it.status == LOADING -> switchProgressDialog(true)
                it.status == SUCCESS -> {
                    switchProgressDialog(false)
                    onSuccessResponse(it.data, it.request)
                }
            }
        })
    }

    private fun subscribeViewModelToErrorHandler() {
        errorHandlerLiveData()?.observe(this, Observer<DataResponse> {
            if (it.error == NOT_NETWORK_MODE) {
                view!!.showSnackbar("Off line mode on", Snackbar.LENGTH_LONG)
            }
            onFailureResponse(ViewBasicError(it.error), it.request)
        })
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

    private fun switchProgressDialog(show: Boolean) {
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

    abstract fun onFailureResponse(errorView: ViewBasicError?, request: String?)

    @LayoutRes
    abstract fun fragmentLayout(): Int

    abstract fun dataResponseLiveData(): MutableLiveData<DataResponse>?

    abstract fun errorHandlerLiveData(): MutableLiveData<DataResponse>?


}