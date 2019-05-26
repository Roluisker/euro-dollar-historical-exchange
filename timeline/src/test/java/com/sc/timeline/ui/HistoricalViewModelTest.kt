package com.sc.timeline.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sc.core.annotation.net.ERROR
import com.sc.core.annotation.net.LOADING

import com.sc.core.annotation.net.SUCCESS
import com.sc.core.annotation.net.TIME_SERIES

import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.timeline.TestUtils
import com.sc.timeline.repository.HistoricalRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK

import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HistoricalViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var historicalRepository: HistoricalRepositoryImpl

    @MockK
    lateinit var timeSeriesRemote: TimeSeriesRemote

    lateinit var historicalViewModel: HistoricalViewModel

    val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        historicalViewModel = HistoricalViewModel(historicalRepository, dispatcher, dispatcher)
    }

    @Test
    fun showHistoricalSuccessTest() {

        every { timeSeriesRemote.rates.rateItem } returns TestUtils.mapResult()

        val response = DataResponse.success(timeSeriesRemote, TIME_SERIES)

        coEvery { historicalRepository.fetchHistorical(any(), any(), TIME_SERIES) } returns response

        historicalViewModel.liveDataResponse.observeForever { }

        historicalViewModel.showHistorical("", "")

        assert(historicalViewModel.liveDataResponse.value != null)
        assert(historicalViewModel.liveDataResponse.value!!.status == SUCCESS)

    }

    @Test
    fun showHistoricalExpectedErrorTest() {

        every { timeSeriesRemote.rates.rateItem } returns TestUtils.mapResult()

        val errorResponse = DataResponse.error(1, TIME_SERIES)

        coEvery { historicalRepository.fetchHistorical(any(), any(), TIME_SERIES) } returns errorResponse

        historicalViewModel.liveDataResponse.observeForever { }

        historicalViewModel.showHistorical("", "")

        assert(historicalViewModel.liveDataResponse.value!!.status == LOADING)
        assert(historicalViewModel.liveGraph.value == null)
        assert(historicalViewModel.liveDataErrorResponse.value!!.status == ERROR)

    }

    @Test
    fun showHistoricalUnexpectedErrorTest() {

        coEvery {
            historicalRepository.fetchHistorical(
                any(),
                any(),
                TIME_SERIES
            )
        } coAnswers { throw Exception("UnexpectedError") }

        historicalViewModel.showHistorical("", "")

        assert(historicalViewModel.liveDataResponse.value!!.status == LOADING)
        assert(historicalViewModel.liveGraph.value == null)
        assert(historicalViewModel.liveDataErrorResponse.value!!.status == ERROR)

    }


}