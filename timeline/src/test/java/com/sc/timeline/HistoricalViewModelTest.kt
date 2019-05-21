package com.sc.timeline

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.timeline.repository.history.HistoricalRepositoryImpl
import com.sc.timeline.ui.HistoricalFragment
import com.sc.timeline.ui.HistoricalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HistoricalViewModelTest {

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
    private lateinit var liveData: MutableLiveData<DataResponse<Any>>

    @Mock
    private val job = SupervisorJob()

    var scope = CoroutineScope(Dispatchers.Default + job)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setupContext()
       // historicalViewModel = HistoricalViewModel(historicalRepository, context)
        historicalViewModel.liveData = liveData
        historicalViewModel.historicalRepository = historicalRepository
        historicalViewModel.scope = scope
    }

    private fun setupContext() {
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
        Mockito.`when`<String>(context.packageName).thenReturn("com.sc.timeline.test")
    }

    @Test
    //suspend fun showHistoricalTest() {
    fun showHistoricalTest()  = runBlocking<Unit> {


            val timeSeriesRemote = TimeSeriesRemote(true)

            val response = DataResponse.Success(timeSeriesRemote, TIME_SERIES)


            Mockito.`when`<DataResponse<TimeSeriesRemote>>(historicalRepository.showHistorical("", "", TIME_SERIES))
                .thenReturn(response)


            //Mockito.`when`<Any>(historicalViewModel.liveData.postValue(Mockito.any())).thenReturn(response)

            //historicalViewModel.showHistorical("", "")

            //Mockito.verify(historicalRepository).showHistorical("", "", TIME_SERIES)

            //Mockito.verify(historicalFragment, Mockito.atLeast(1)).loadHistorical("", "")
             //historicalViewModel.showHistorical("", "")
            Mockito.verify(historicalViewModel, Mockito.atLeast(1)).showHistorical("", "")

            //Mockito.atLeast()


        //whenever(service.fetchVideos(123)).thenReturn(call)

    }

    @Test
    fun useAppContext() {
        Assert.assertEquals("com.sc.timeline.test", context.packageName)
    }

}