package com.sc.timeline.ui

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sc.core.annotation.net.FixerRequest
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.net.ViewBasicError
import com.sc.core.ui.coreComponent
import com.sc.timeline.di.DaggerTimelineComponent
import com.sc.timeline.ui.di.HistoricalFragmentModule
import kotlinx.android.synthetic.main.historical_fragment.*
import javax.inject.Inject
import com.sc.timeline.R
import android.app.DatePickerDialog
import android.view.View
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import com.sc.core.*
import com.sc.core.net.DataResponse
import com.sc.timeline.model.GraphLineData
import lecho.lib.hellocharts.model.*
import org.joda.time.DateTime
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.*

const val DEFAULT_HISTORICAL = 20

open class HistoricalFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var historicalViewModel: HistoricalViewModel

    private lateinit var startDateDialog: DatePickerDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModelToGrapData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        loadHistorical(DateUtilities.todayMinusDays(DEFAULT_HISTORICAL), DateUtilities.todayMinusDays(1))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    private fun loadHistorical(start: String, end: String) {
        if (isHistoricalViewModelAvailable()) {
            historicalViewModel.showHistorical(start, end)
        }
    }

    override fun onSuccessResponse(data: Any?, @FixerRequest request: String?) {
        request?.let {
            when (it) {
                TIME_SERIES -> {
                }
            }
        }
    }

    override fun onFailureResponse(errorView: ViewBasicError?, @FixerRequest request: String?) {
        errorView?.let {
            Timber.i(it.code.toString() + "--" + request)
        }
    }

    private fun setListeners() {
        dateSelector.setOnClickListener {
            selectStartDate()
        }
    }

    private fun subscribeViewModelToGrapData() {
        if (isHistoricalViewModelAvailable()) {
            historicalViewModel.liveGraph.observe(this, Observer<GraphLineData> {
                showChartLine(it)
            })
        }
    }

    private fun showChartLine(points: GraphLineData) {

        val line = Line(points.yAxisValues)
        val lines = ArrayList<Line>()
        val lineChar = LineChartData()

        line.apply {
            color = ContextCompat.getColor(context!!, R.color.colorBlack)
            setHasLabels(true)
            setHasLabelsOnlyForSelected(true)
        }

        lines.add(line)

        lineChar.lines = lines

        val axis = Axis().apply {
            values = points.axisValues
            textSize = 12
            textColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
        }

        lineChar.axisXBottom = axis

        val yAxis = Axis().apply {
            name = CoreConstants.EMPTY
            textColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
            textSize = 16
        }

        lineChar.axisYLeft = yAxis

        lineChart.apply {
            lineChartData = lineChar
            val viewport = Viewport(lineChart.maximumViewport)
            maximumViewport = viewport
            currentViewport = viewport
            AnimationUtil.animate(lineChart, R.anim.fade_in, context)
        }

    }

    fun selectStartDate() {
        startDateDialog = initDatePicker()
        startDateDialog.show()
    }

    private fun initDatePicker(): DatePickerDialog {

        val calendar = DateUtilities.nativeCalendarToday()

        var picker = DatePickerDialog(
            context,
            this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        picker.datePicker.minDate = DateUtilities.nativeCalendarDaysAgo(DEFAULT_HISTORICAL).timeInMillis
        picker.datePicker.maxDate = DateUtilities.nativeCalendarDaysAgo(1).timeInMillis

        return picker

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        val currentDayOfMo: Int = if (dayOfMonth == 31) {
            30
        } else {
            dayOfMonth
        }

        var mo = month + 1

        loadHistorical(
            DateUtilities.format(TODAY_TIME_FORMAT_DATE, DateTime("$year-$mo-$currentDayOfMo")),
            DateUtilities.todayMinusDays(1)
        )

    }

    private fun initDependencyInjection() {
        val coreComponent = coreComponent()
        if (coreComponent != null) {
            DaggerTimelineComponent
                .builder()
                .coreComponent(coreComponent)
                .historicalFragmentModule(HistoricalFragmentModule(this))
                .build()
                .inject(this)
        }
    }

    override fun dataResponseLiveData(): MutableLiveData<DataResponse>? {
        if (isHistoricalViewModelAvailable()) {
            return historicalViewModel.liveDataResponse
        }
        return null
    }

    override fun errorHandlerLiveData(): MutableLiveData<DataResponse>? {
        if (isHistoricalViewModelAvailable()) {
            historicalViewModel.liveDataErrorResponse
        }
        return null
    }

    private fun isHistoricalViewModelAvailable(): Boolean = ::historicalViewModel.isInitialized

    override fun fragmentLayout(): Int = R.layout.historical_fragment

}