package com.sc.timeline.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson

import com.sc.core.annotation.net.ERROR
import com.sc.core.annotation.net.SUCCESS
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.api.MoneyApi
import com.sc.core.db.TimeSeriesDao
import com.sc.core.model.local.TimeSeries
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.core.net.ERROR_NOT_SET
import com.sc.core.net.RESPONSE_FROM_LOCAL
import com.sc.timeline.FROM_GSON
import com.sc.timeline.TestUtils
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.UnknownHostException

@RunWith(JUnit4::class)
class HistoricalRepositoryImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var moneyApi: MoneyApi

    @MockK
    lateinit var timeSeriesRemote: TimeSeriesRemote

    @MockK
    lateinit var timeSeriesDao: TimeSeriesDao

    lateinit var historicalImpl: HistoricalRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        historicalImpl = HistoricalRepositoryImpl(moneyApi, timeSeriesDao)
    }

    @Test
    fun fetchHistoricalSuccessTest() = runBlocking {

        every { timeSeriesRemote.rates.rateItem } returns TestUtils.mapResult()

        every { mockkClass(Gson::class).toJson(any()) } returns FROM_GSON

        every { timeSeriesRemote.end_date } returns ""
        every { timeSeriesRemote.start_date } returns ""

        coEvery { historicalImpl.fetchHistorical(any(), any(), TIME_SERIES) } returns DataResponse.success(
            timeSeriesRemote,
            TIME_SERIES
        )

        coEvery { moneyApi.getMoneyTimeSeriesByDateAsync(any(), any()).await() } returns timeSeriesRemote

        val fetchResponse = historicalImpl.fetchHistorical("", "", TIME_SERIES)

        assertNotNull(fetchResponse)
        assert(fetchResponse is DataResponse)
        assert(fetchResponse.error == ERROR_NOT_SET)
        assert(fetchResponse.status == SUCCESS)
        assert(fetchResponse.request == TIME_SERIES)

    }

    @Test
    fun fetchHistoricalErrorTest() = runBlocking {

        coEvery {
            moneyApi.getMoneyTimeSeriesByDateAsync(any(), any()).await()
        } coAnswers { throw Exception("UnexpectedError") }

        val fetchResponse = historicalImpl.fetchHistorical("", "", TIME_SERIES)

        assertNotNull(fetchResponse)
        assert(fetchResponse is DataResponse)
        assert(fetchResponse.error == HISTORICAL_UNEXPECTED_ERROR)
        assert(fetchResponse.status == ERROR)
        assert(fetchResponse.request == TIME_SERIES)

    }

    @Test
    fun errorHandlerDatabaseWithoutDataTest() = runBlocking {

        coEvery { timeSeriesDao.series() } returns emptyList()

        val error = historicalImpl.errorHandler(UnknownHostException(""), TIME_SERIES)

        assertNotNull(error)
        assert(error is DataResponse)
        assert(error.error == HISTORICAL_UNEXPECTED_ERROR)

    }

    @Test
    fun errorHandlerGenericExceptionTest() = runBlocking {

        val error = historicalImpl.errorHandler(Exception(""), TIME_SERIES)

        assertNotNull(error)
        assert(error is DataResponse)
        assert(error.error == HISTORICAL_UNEXPECTED_ERROR)

    }

    @Test
    fun errorHandlerUnexpectedExceptionTest() = runBlocking {

        coEvery { timeSeriesDao.series() } coAnswers { throw Exception("UnexpectedError") }

        val error = historicalImpl.errorHandler(UnknownHostException(""), TIME_SERIES)

        assertNotNull(error)
        assert(error is DataResponse)
        assert(error.error == HISTORICAL_UNEXPECTED_ERROR)

    }

    @Test
    fun errorHandlerDataFromLocalTest() = runBlocking {

        var timeSeries = TimeSeries()
        timeSeries.rateItemJson = FROM_GSON

        var timeSeriesList = listOf(timeSeries)

        coEvery { timeSeriesDao.series() } returns timeSeriesList

        val dataResponse = historicalImpl.errorHandler(UnknownHostException(""), TIME_SERIES)

        assertNotNull(dataResponse)
        assert(dataResponse is DataResponse)
        assert(dataResponse.status == SUCCESS)
        assert(dataResponse.responseFrom == RESPONSE_FROM_LOCAL)

    }

}