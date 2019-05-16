package com.sc.lydianlion.ui.view.history

import android.os.Bundle
import androidx.lifecycle.Observer
import com.sc.core.BaseFragment
import com.sc.lydianlion.R
import timber.log.Timber
import javax.inject.Inject

class HistoricalFragment : BaseFragment() {

    @Inject
    lateinit var historicalViewModel: HistoricalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        historicalViewModel.showHistorical("2012-05-01", "2012-05-25")

        historicalViewModel.moneyHistory.observe(this, Observer {
            Timber.d("Response: " + it.success.toString())
        })

    }

    override fun fragmentLayout(): Int = R.layout.historical_fragment

}