package com.sc.timeline

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.api.MoneyApi
import com.sc.core.model.local.TimeSeries
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.timeline.model.GraphLineData
import com.sc.timeline.repository.history.HistoricalRepositoryImpl
import com.sc.timeline.ui.HistoricalFragment
import com.sc.timeline.ui.HistoricalViewModel
import kotlinx.coroutines.*
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
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
    private lateinit var api: MoneyApi

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

    @Mock
    private var scope = CoroutineScope(Dispatchers.Default + job)

    // asi estab a
    //private var scope = CoroutineScope(Dispatchers.Default + job)

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

    @Test
    fun repositoryCallShowHistoricalTest() = runBlocking<Unit> {

        val historicalViewModel = Mockito.spy(historicalViewModel)
        val historicalRepositoryPy = Mockito.spy(historicalRepository)

        historicalViewModel.historicalRepository = historicalRepositoryPy

        historicalViewModel.showHistorical("", "")

        Mockito.verify(historicalRepositoryPy, Mockito.times(1)).showHistorical(
            "",
            "", TIME_SERIES
        )

    }

}