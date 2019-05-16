package com.sc.lydianlion.ui.view.history

import android.os.Bundle
import com.sc.core.BaseFragment
import com.sc.lydianlion.R
import javax.inject.Inject

class HistoricalFragment : BaseFragment() {

    @Inject
    lateinit var historicalViewModel: HistoricalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historicalViewModel.testInjection()
    }

    override fun fragmentLayout(): Int = R.layout.historical_fragment

}