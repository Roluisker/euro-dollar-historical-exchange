package com.sc.timeline.ui

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sc.core.BaseFragment
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.net.BasicError
import com.sc.core.net.DataResponse
import com.sc.core.ui.coreComponent
import com.sc.timeline.di.DaggerTimelineComponent
import com.sc.timeline.ui.di.HistoricalFragmentModule
import kotlinx.android.synthetic.main.historical_fragment.*
import javax.inject.Inject
import androidx.appcompat.app.AlertDialog
import com.sc.core.CoreConstants.Companion.JPY
import com.sc.core.CoreConstants.Companion.MAX_USD_RANGE
import com.sc.core.CoreConstants.Companion.MAX_YEN_RANGE
import com.sc.core.CoreConstants.Companion.USD
import com.sc.core.DateUtilities
import com.sc.timeline.R
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.Viewport

const val DEFAULT_HISTORICAL = 180

class HistoricalFragment : BaseFragment() {

    @Inject
    lateinit var historicalViewModel: HistoricalViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        historicalViewModel.showHistorical(DateUtilities.todayMinusDays(DEFAULT_HISTORICAL), DateUtilities.today())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onSuccessResponse(data: Any?, @FixerRequest request: String?) {
        request?.let {
            when (it) {
                TIME_SERIES -> showChartLine(data as LineChartData)
            }
        }
    }

    override fun onFailureResponse(error: BasicError?, @FixerRequest request: String?) {

    }

    private fun setListeners() {
        euroTo.setOnClickListener {
            showMoneyOptions()
        }
    }

    private fun showMoneyOptions() {
        val checkedItem = 0
        arrayOf(USD, JPY).let {

            AlertDialog.Builder(context!!).apply {
                setTitle(getString(R.string.euro_to))
                setSingleChoiceItems(
                    it, checkedItem
                ) { dialog, which ->
                    historicalViewModel.euroToKey = it[which]
                }
                setPositiveButton(getString(R.string.done)) { dialog, which ->
                    run {
                        dialog.dismiss()
                        currentMoney(historicalViewModel.euroToKey)
                        historicalViewModel.showHistorical(
                            DateUtilities.todayMinusDays(DEFAULT_HISTORICAL),
                            DateUtilities.today()
                        )
                    }
                }
                create()
                show()
            }

        }
    }

    private fun currentMoney(key: String) {
        key?.let {
            when (it) {
                USD -> {
                    euroTo.text = getString(R.string.euro_dollar)
                    historicalViewModel.maxRange = MAX_USD_RANGE
                }
                JPY -> {
                    euroTo.text = getString(R.string.euro_yen)
                    historicalViewModel.maxRange = MAX_YEN_RANGE
                }
            }
        }
    }

    private fun showChartLine(lineChar: LineChartData) {
        lineChart.apply {
            lineChartData = lineChar
            val viewport = Viewport(lineChart.maximumViewport)
            maximumViewport = viewport
            currentViewport = viewport
        }
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