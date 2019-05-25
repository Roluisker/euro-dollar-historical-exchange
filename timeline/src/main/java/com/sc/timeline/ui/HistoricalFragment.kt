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
import androidx.appcompat.app.AlertDialog
import com.sc.core.CoreConstants.Companion.JPY
import com.sc.core.CoreConstants.Companion.MAX_USD_RANGE
import com.sc.core.CoreConstants.Companion.MAX_YEN_RANGE
import com.sc.core.CoreConstants.Companion.USD
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

const val DEFAULT_HISTORICAL = 30

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
        //loadHistorical(DateUtilities.todayMinusDays(DEFAULT_HISTORICAL), DateUtilities.today())
        //loadHistorical("2019-03-01", "2019-03-20")
        loadHistorical("2019-03-01", "2019-03-20")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    fun loadHistorical(start: String, end: String) {
        if (isHistoricalViewModelAvailable()) {
            val isCalled = historicalViewModel.showHistorical(start, end)
            if (isCalled) {
                startDate.text = start
                endDate.text = end
                endDate.isEnabled = false
            }
        }
    }

    /*In the future you can handle the response behaibuur by response  */
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
        euroTo.setOnClickListener {
            showMoneyOptions()
        }
        startDate.setOnClickListener {
            selectStartDate()
        }
    }

    private fun showMoneyOptions() {
        val checkedItem = 0
        arrayOf(USD).let {

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
        startDateDialog.setTitle("Fecha")
        startDateDialog.show()
    }

    private fun initDatePicker(): DatePickerDialog {
        val calendar = Calendar.getInstance()
        return DatePickerDialog(
            context,
            this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        val currentDayOfMo: Int = if (dayOfMonth == 31) {
            30
        } else {
            dayOfMonth
        }

        historicalViewModel.showHistorical(
            DateUtilities.format(TODAY_TIME_FORMAT_DATE, DateTime("$year-$month-$currentDayOfMo")),
            historicalViewModel.currentEndDate
        )
        startDate.text = DateUtilities.format(TODAY_TIME_FORMAT_DATE, DateTime("$year-$month-$currentDayOfMo"))

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