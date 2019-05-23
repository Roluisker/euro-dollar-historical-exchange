package com.sc.timeline

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.model.local.TimeSeries
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.timeline.model.GraphLineData
import com.sc.timeline.repository.history.HistoricalRepositoryImpl
import com.sc.timeline.ui.HistoricalFragment
import com.sc.timeline.ui.HistoricalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.ArrayList
import java.util.HashMap

@RunWith(JUnit4::class)
class HistoricalTest {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Application

    @Mock
    private lateinit var historicalRepository: HistoricalRepositoryImpl

    @Mock
    private lateinit var historicalFragment: HistoricalFragment

    @Mock
    private lateinit var historicalViewModel: HistoricalViewModel

    @Mock
    private lateinit var liveData: MutableLiveData<DataResponse>

    @Mock
    var successfulLiveData = MutableLiveData<DataResponse>()

    @Mock
    private val job = SupervisorJob()

    private var scope = CoroutineScope(Dispatchers.Default + job)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setupContext()
        historicalViewModel.liveDataResponse = liveData
        historicalViewModel.historicalRepository = historicalRepository
        historicalViewModel.scope = scope
    }

    private fun setupContext() {
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
        Mockito.`when`<String>(context.packageName).thenReturn("com.sc.timeline.test")
    }

    /*
    @Test
    fun useAppContext() {
        Assert.assertEquals("com.sc.timeline.test", context.packageName)
    }*/

    /*
    @Test
    fun repositoryCallShowHistoricalTest() = runBlocking<Unit> {

        val historicalViewModel = Mockito.spy(historicalViewModel)
        val historicalRepositoryPy = Mockito.spy(historicalRepository)
        val liveGraPy = Mockito.spy(liveGraphMock)

        historicalViewModel.historicalRepository = historicalRepositoryPy


        historicalViewModel.showHistorical("", "")

        Mockito.verify(historicalRepositoryPy, Mockito.times(1)).showHistorical(
            "",
            "", TIME_SERIES
        )

    }*/

    @Mock
    private lateinit var timeSeriesRemoteMock: TimeSeriesRemote

    @Mock
    private lateinit var lineChar: LineChartData

    @Mock
    private lateinit var hasmMock: HashMap<Int, Any>

    @Mock
    private lateinit var rateItem: HashMap<String, HashMap<String, String>>

    @Mock
    private lateinit var ratesMock: TimeSeries

    @Mock
    private lateinit var liveGraphMock: MutableLiveData<GraphLineData>

    @Test
    fun showHistoricalShowLoaderTest() = runBlocking<Unit> {

        val historicalViewModel = Mockito.spy(historicalViewModel)
        val livePy = Mockito.spy(liveData)
        val repositoryPy = Mockito.spy(historicalRepository)
        val timeSeriesPy = Mockito.spy(timeSeriesRemoteMock)

        val ratesPy = Mockito.spy(ratesMock)
        val rateItemPy = Mockito.spy(rateItem)
        val liveGraPy = Mockito.spy(liveGraphMock)

        historicalViewModel.liveDataResponse = livePy
        historicalViewModel.liveGraph = liveGraPy
        timeSeriesPy.rates = ratesPy
        timeSeriesPy.rates.rateItem = rateItemPy

        val response = DataResponse.success(timeSeriesPy, TIME_SERIES)

        Mockito.`when`<DataResponse>(repositoryPy.showHistorical("", "", TIME_SERIES))
            .thenReturn(response)

        historicalViewModel.showHistorical("", "")

        Mockito.verify(historicalViewModel, Mockito.times(1)).dataToLineChartData(response)

    }
    

}