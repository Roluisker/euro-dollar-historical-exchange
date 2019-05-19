package com.sc.timeline.ui

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sc.core.BaseFragment
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.net.BasicError
import com.sc.core.net.DataResponse
import com.sc.core.ui.coreComponent
import com.sc.timeline.HistoricalViewModel
import com.sc.timeline.R
import com.sc.timeline.di.DaggerTimelineComponent
import com.sc.timeline.ui.di.HistoricalFragmentModule
import timber.log.Timber
import javax.inject.Inject

class HistoricalFragment : BaseFragment() {

    @Inject
    lateinit var historicalViewModel: HistoricalViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        historicalViewModel.showHistorical("2012-05-01", "2012-05-25")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onSuccessResponse(data: Any?, @FixerRequest request: String?) {
        Timber.d(data.toString(), request)
    }

    override fun onFailureResponse(error: BasicError?, @FixerRequest request: String?) {

    }

    private fun initDependencyInjection() =
        DaggerTimelineComponent
            .builder()
            .coreComponent(coreComponent())
            .historicalFragmentModule(HistoricalFragmentModule(this))
            .build()
            .inject(this)

    override fun mutableLiveData(): MutableLiveData<DataResponse<Any>> = historicalViewModel.liveData

    override fun fragmentLayout(): Int = R.layout.historical_fragment

}