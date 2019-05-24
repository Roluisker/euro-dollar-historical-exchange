package com.sc.timeline.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sc.core.annotation.net.ERROR
import com.sc.core.annotation.net.SUCCESS
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.api.MoneyApi
import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.core.net.ERROR_NOT_SET
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HistoricalRepositoryImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var moneyApi: MoneyApi

    @MockK
    lateinit var timeSeriesRemote: TimeSeriesRemote

    lateinit var historicalImpl: HistoricalRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        historicalImpl = HistoricalRepositoryImpl(moneyApi)
    }

    @Test
    fun fetchHistoricalSuccessTest() = runBlocking {

        coEvery { historicalImpl.fetchHistorical(any(), any(), TIME_SERIES) } returns DataResponse.success(
            timeSeriesRemote,
            TIME_SERIES
        )

        coEvery { moneyApi.getMoneyTimeSeriesByDateAsync2(any(), any()).await() } returns timeSeriesRemote

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
            moneyApi.getMoneyTimeSeriesByDateAsync2(any(), any()).await()
        } coAnswers { throw Exception("UnexpectedError") }

        val fetchResponse = historicalImpl.fetchHistorical("", "", TIME_SERIES)

        assertNotNull(fetchResponse)
        assert(fetchResponse is DataResponse)
        assert(fetchResponse.error == HISTORICAL_UNEXPECTED_ERROR)
        assert(fetchResponse.status == ERROR)
        assert(fetchResponse.request == TIME_SERIES)

    }

}