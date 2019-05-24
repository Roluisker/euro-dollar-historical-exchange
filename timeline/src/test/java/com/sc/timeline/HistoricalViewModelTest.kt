package com.sc.timeline

import android.content.Context

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sc.core.CoreConstants.Companion.EUR
import com.sc.core.CoreConstants.Companion.JPY
import com.sc.core.CoreConstants.Companion.USD
import com.sc.core.annotation.net.ERROR

import com.sc.core.annotation.net.SUCCESS
import com.sc.core.annotation.net.TIME_SERIES

import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.timeline.repository.history.HistoricalRepositoryImpl
import com.sc.timeline.ui.HistoricalViewModel
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
    lateinit var context: Context

    @MockK
    lateinit var timeSeriesRemote: TimeSeriesRemote

    lateinit var historicalViewModel: HistoricalViewModel

    val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        historicalViewModel = HistoricalViewModel(historicalRepository, context, dispatcher, dispatcher)
    }

    @Test
    fun showHistoricalSuccessTest() {

        every { timeSeriesRemote.rates.rateItem } returns mapResult()

        val response = DataResponse.success(timeSeriesRemote, TIME_SERIES)

        coEvery { historicalRepository.fetchHistorical(any(), any(), TIME_SERIES) } returns response

        historicalViewModel.liveDataResponse.observeForever { }

        historicalViewModel.showHistorical("", "")

        assert(historicalViewModel.liveDataResponse.value != null)
        assert(historicalViewModel.liveDataResponse.value!!.status == SUCCESS)

    }

    @Test
    fun showHistoricalExpectedErrorTest() {

        every { timeSeriesRemote.rates.rateItem } returns mapResult()

        val errorResponse = DataResponse.error(1, TIME_SERIES)

        coEvery { historicalRepository.fetchHistorical(any(), any(), TIME_SERIES) } returns errorResponse

        historicalViewModel.liveDataResponse.observeForever { }

        historicalViewModel.showHistorical("", "")

        assert(historicalViewModel.liveDataResponse.value == null)
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

        assert(historicalViewModel.liveDataResponse.value == null)
        assert(historicalViewModel.liveGraph.value == null)
        assert(historicalViewModel.liveDataErrorResponse.value!!.status == ERROR)

    }

    fun mapResult(): HashMap<String, HashMap<String, String>> {

        return hashMapOf(
            "2019-03-01" to
                    hashMapOf(
                        USD to "1.1",
                        JPY to "2",
                        EUR to "1"
                    )
        )

    }

}