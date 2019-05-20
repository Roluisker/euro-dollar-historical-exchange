package com.sc.timeline.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sc.core.BaseFragment
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.BasicError
import com.sc.core.net.DataResponse
import com.sc.core.ui.coreComponent
import com.sc.timeline.di.DaggerTimelineComponent
import com.sc.timeline.ui.di.HistoricalFragmentModule
import kotlinx.android.synthetic.main.historical_fragment.*
import javax.inject.Inject
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.model.Viewport
import androidx.appcompat.app.AlertDialog
import com.sc.core.CoreConstants
import com.sc.timeline.R

class HistoricalFragment : BaseFragment() {

    @Inject
    lateinit var historicalViewModel: HistoricalViewModel
    var euroToKey: String = CoreConstants.USD

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        historicalViewModel.showHistorical("2012-05-01", "2012-05-25")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onSuccessResponse(data: Any?, @FixerRequest request: String?) {
        request?.let {
            when (it) {
                TIME_SERIES -> showChartLine(data as TimeSeriesRemote)
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
        arrayOf(CoreConstants.USD, CoreConstants.JPY).let {

            AlertDialog.Builder(context!!).apply {
                setTitle(getString(R.string.euro_to))
                setSingleChoiceItems(
                    it, checkedItem
                ) { dialog, which ->
                    euroToKey = it[which]
                }
                setPositiveButton(getString(R.string.hide)) { dialog, which ->
                    run {
                        dialog.dismiss()
                        currentMoney(euroToKey)
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
                CoreConstants.USD -> {
                    euroTo.text = getString(R.string.euro_dollar)
                }
                CoreConstants.JPY -> {
                    euroTo.text = getString(R.string.euro_yen)
                }
            }
        }
    }

    private fun showChartLine(rateItem: TimeSeriesRemote) {

        rateItem.rates.rateItem.keys.toTypedArray()

        var axisData =
            rateItem.rates.rateItem.keys.toTypedArray() //arrayOf("Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec")
        var yAxisData = intArrayOf(50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18)

        val yAxisValues = ArrayList<PointValue>()
        val axisValues = ArrayList<AxisValue>()

        val line = Line(yAxisValues).setColor(Color.parseColor("#9C27B0"))

        for (i in 0 until axisData.size) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(axisData[i]))
        }

        for (i in 0 until yAxisData.size) {
            yAxisValues.add(PointValue(i.toFloat(), yAxisData[i].toFloat()))
        }

        val lines = ArrayList<Line>()
        lines.add(line)

        val data = LineChartData()
        data.lines = lines

        val axis = Axis()
        axis.values = axisValues
        axis.textSize = 16
        axis.textColor = Color.parseColor("#03A9F4")
        data.axisXBottom = axis

        val yAxis = Axis()
        yAxis.name = "Euro"
        yAxis.textColor = Color.parseColor("#03A9F4")
        yAxis.textSize = 16
        data.axisYLeft = yAxis

        lineChart.lineChartData = data
        val viewport = Viewport(lineChart.maximumViewport)
        viewport.top = 110f
        lineChart.maximumViewport = viewport
        lineChart.currentViewport = viewport

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