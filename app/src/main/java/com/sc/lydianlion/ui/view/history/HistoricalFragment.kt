package com.sc.lydianlion.ui.view.history

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sc.lydianlion.core.BaseFragment
import com.sc.lydianlion.core.net.BasicError
import com.sc.lydianlion.core.net.DataResponse
import com.sc.lydianlion.R
import com.sc.lydianlion.core.annotation.net.FixerRequest
import timber.log.Timber
import javax.inject.Inject

class HistoricalFragment : BaseFragment() {

    @Inject
    lateinit var historicalViewModel: HistoricalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historicalViewModel.showHistorical("2012-05-01", "2012-05-25")
    }

    override fun onSuccessResponse(data: Any?, @FixerRequest request: String?) {
        Timber.d(data.toString(), request)
    }

    override fun onFailureResponse(error: BasicError?, @FixerRequest request: String?) {

    }

    override fun fragmentLayout(): Int = R.layout.historical_fragment

    override fun mutableLiveData(): MutableLiveData<DataResponse<Any>> = historicalViewModel.liveData

}