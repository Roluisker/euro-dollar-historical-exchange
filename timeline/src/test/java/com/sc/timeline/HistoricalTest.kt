package com.sc.timeline

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.model.local.TimeSeries
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.timeline.repository.history.HistoricalRepositoryImpl
import com.sc.timeline.ui.HistoricalFragment
import com.sc.timeline.ui.HistoricalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import lecho.lib.hellocharts.model.LineChartData
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
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

    @Test
    fun useAppContext() {
        Assert.assertEquals("com.sc.timeline.test", context.packageName)
    }

    @Test
    fun repositoryCallShowHistoricalTest() = runBlocking<Unit> {

        val historicalViewModel = Mockito.spy(historicalViewModel)
        historicalViewModel.showHistorical("", "")
        Mockito.verify(historicalRepository, Mockito.times(1)).showHistorical(
            "",
            "", TIME_SERIES
        )

    }

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

    @Test
    fun showHistoricalShowLoaderTest() = runBlocking<Unit> {

        val historicalViewModel = Mockito.spy(historicalViewModel)
        val livePy = Mockito.spy(liveData)
        val repositoryPy = Mockito.spy(historicalRepository)
        val timeSeriesPy = Mockito.spy(timeSeriesRemoteMock)
        //val hasPy = Mockito.spy(hasmMock)
        val ratesPy = Mockito.spy(ratesMock)
        val rateItemPy = Mockito.spy(rateItem)

        historicalViewModel.liveDataResponse = livePy
        timeSeriesPy.rates = ratesPy
        timeSeriesPy.rates.rateItem = rateItemPy
        //val timeSeriesRemote = TimeSeriesRemote(true)

        val response = DataResponse.success(timeSeriesPy, TIME_SERIES)

        Mockito.`when`<DataResponse>(repositoryPy.showHistorical("", "", TIME_SERIES))
            .thenReturn(response)

        //Mockito.`when`<LineChartData>(historicalViewModel.dataToLineChartData(response.data as TimeSeriesRemote))
        //    .thenReturn(lineChar)

        Mockito.`when`<HashMap<Int, Any>>(historicalViewModel.dataToLineChartData(timeSeriesPy)).thenReturn(Mockito.any(HashMap::class.java))

        historicalViewModel.showHistorical("", "")

        Mockito.verify(livePy, Mockito.times(1)).postValue(response)

        //val loader = DataResponse.Loading<DataResponse<Any>>(TIME_SERIES)

        // problema, el DataResponse se llama dos veces con diferentes valores y explota.
        //
        // Mockito.`when`<Unit>(livePy.postValue(loader))
        //    .thenReturn(response)

        /*
        `when`(livePy.postValue(response))
            .thenReturn()
            .thenReturn(1)
            .thenReturn(-1) */

        //historicalViewModel.showHistorical("", "")

        //tratando de probar que el postValue se llame exactamente 2 veces al llamar showHistorical
        //Mockito.verify(livePy, Mockito.times(2)).postValue(response)

    }


    /*
    // "2019-03-01", "2019-03-20"
    val startDate = "2019-03-01"
    val endDate = "2019-03-20"

    val timeSeriesRemote = TimeSeriesRemote(true)

    val response = DataResponse.Success(timeSeriesRemote, TIME_SERIES)

    // Mockito.verify(view).updateList(Mockito.argThat { argument -> argument?.size == 4 })

    Mockito.`when`<DataResponse<TimeSeriesRemote>>(historicalRepository.showHistorical("2019-03-01",
        "2019-03-20", TIME_SERIES))
        .thenReturn(response)

    historicalViewModel.showHistorical("2019-03-01", "2019-03-20")
    Mockito.verify(historicalRepository).showHistorical(Mockito.eq("2019-03-01"), Mockito.eq("2019-03-20"), TIME_SERIES)
    */
    /*


    Mockito.`when`<DataResponse<TimeSeriesRemote>>(historicalRepository.showHistorical("", "", TIME_SERIES))
        .thenReturn(response)

    //historicalRepository.showHistorical("", "", TIME_SERIES)

    //Mockito.verify(historicalViewModel, Mockito.atLeast(1)).showHistorical("", "")
    Mockito.verify(historicalViewModel, Mockito.calls(2)).showHistorical("", "")

   // Mockito.verify()
   */

    /*

            //Mockito.`when`<Any>(historicalViewModel.liveDataResponse.postValue(Mockito.any())).thenReturn(response)

        //historicalViewModel.showHistorical("", "")

        //Mockito.verify(historicalRepository).showHistorical("", "", TIME_SERIES)

        //Mockito.verify(historicalFragment, Mockito.atLeast(1)).loadHistorical("", "")
        //historicalViewModel.showHistorical("", "")

        //Mockito.atLeast()

        //whenever(service.fetchVideos(123)).thenReturn(call)

     */


}