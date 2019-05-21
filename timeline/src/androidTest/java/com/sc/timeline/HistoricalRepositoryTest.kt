package com.sc.timeline

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.core.internal.deps.guava.base.Joiner.on
import com.sc.core.api.MoneyApi
import com.sc.timeline.repository.history.HistoricalRepositoryImpl
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.nhaarman.mockitokotlin2.*
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.model.remote.TimeSeriesRemote
import org.junit.Before
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.*
import retrofit2.Response



@RunWith(JUnit4::class)
class HistoricalRepositoryTest {

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

    @Test
    fun doAction_doesSomething() {

        /*
        val deferred = CompletableDeferred<Response<TimeSeriesRemote>>()

        val mock = mock<HistoricalRepositoryImpl> {
            on { runBlocking { showHistorical("", "", TIME_SERIES) } } doReturn deferred
        }*/

        /*
        val deferred = Deferred<Response<TimeSeriesRemote>>()

        val service = mock<MoneyApi> {
            on { getMoneyTimeSeriesByDateAsync("","") } doReturn GlobalScope.async { deferred }
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