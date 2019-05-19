package com.sc.convert.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.sc.convert.R
import com.sc.convert.di.DaggerConvertComponent
import com.sc.convert.ui.di.ConvertFragmentModule
import com.sc.core.BaseFragment
import com.sc.core.net.BasicError
import com.sc.core.net.DataResponse
import com.sc.core.ui.coreComponent

abstract class ConvertFragment : BaseFragment() {


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onSuccessResponse(data: Any?, request: String?) {

    }

    override fun onFailureResponse(error: BasicError?, request: String?) {

    }

    private fun initDependencyInjection() =
        DaggerConvertComponent
            .builder()
            .coreComponent(coreComponent())
            .convertFragmentModule(ConvertFragmentModule(this))
            .build()
            .inject(this)

    override fun fragmentLayout(): Int = R.layout.convert_fragment


}