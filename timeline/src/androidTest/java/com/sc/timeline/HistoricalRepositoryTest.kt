package com.sc.timeline

import androidx.test.InstrumentationRegistry
import com.sc.core.api.MoneyApi
import com.sc.core.model.remote.TimeSeriesRemote

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

//import org.mockito.Mockito.*
import kotlinx.coroutines.runBlocking

import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.net.DataResponse
import com.sc.timeline.repository.history.HistoricalRepositoryImpl


@RunWith(JUnit4::class)
class HistoricalRepositoryTest {

    //private val service = mock<MoneyApi>()

    /*
    private lateinit var repository: HistoricalRepositoryImpl
    private val service = mock<MoneyApi>()
    */

    /*
    @Before
    fun init() {
        repository = HistoricalRepositoryImpl(service)
    }*/

    /*
    @Test
    fun loadFromNetwork() {

        val deferred = CompletableDeferred<Response<TimeSeriesRemote>>()
        whenever(service.getMoneyTimeSeriesByDateAsync("", "")).thenReturn(deferred)

        val a = verify(service.getMoneyTimeSeriesByDateAsync("", "")).getCompleted()
        assert(a.isSuccessful)
        //val data = repository.showHistorical("", "", TIME_SERIES)
        //verifyNoMoreInteractions(service)

        //whenever(service.getMoneyTimeSeriesByDateAsync("","")).thenReturn()

    }*/

    /*
    @Test
    fun doActionDoesSomething() {

        val timeSeriesRemote = TimeSeriesRemote(true)

        val service = mock<MoneyApi> {
            on {
                runBlocking {
                    getMoneyTimeSeriesByDateAsync("", "")
                }
            } doReturn GlobalScope.async { timeSeriesRemote }
        }


        //verify(timeSeriesRemote.success)

    }*/

    /*
    fun <T : Any, R> KStubbing<T>.onBlocking(m: suspend T.() -> R)
            : OngoingStubbing<R> {
        return runBlocking { `when`(mock.m()) }
    }

    fun <T> verifyBlocking(mock: T, f: suspend T.() -> Unit) {
        val m = verify(mock)
        runBlocking { m.f() }
    }

    fun <T> T.toDeferred() = GlobalScope.async { this@toDeferred }
    */


    @Test
    fun doAction_doesSomething() {

        //val deferred = CompletableDeferred<TimeSeriesRemote>()
        val timeSeriesRemote = TimeSeriesRemote(true)

        val response = DataResponse.Success(timeSeriesRemote, TIME_SERIES)

        val mock = mock<HistoricalRepositoryImpl> {
            on { runBlocking { showHistorical("", "", TIME_SERIES) } } doReturn response
        }

        runBlocking {
           // mock.showHistorical("", "", TIME_SERIES)

            val result = mock.showHistorical("", "")


        }

        /*
        val deferred = Deferred<Response<TimeSeriesRemote>>()

        val service = mock<MoneyApi> {
            on { getMoneyTimeSeriesByDateAsync("", "") } doReturn GlobalScope.async { deferred }
        }*/

        //val deferred = CompletableDeferred<TimeSeriesRemote>()

        //val service = mock<MoneyApi> {
        //    on { getMoneyTimeSeriesByDateAsync("", "") } doReturn GlobalScope.async { deferred }

        //}

    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.sc.timeline.test", appContext.packageName)
    }


    /*
        fun <T> T.toDeferred() = GlobalScope.async { this@toDeferred }

        fun <T : Any, R> KStubbing<T>.onBlocking(m: suspend T.() -> R)
                : OngoingStubbing<R> {
            return runBlocking { Mockito.`when`(mock.m()) }
        }
    */

}