package com.sc.timeline

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sc.core.annotation.net.SUCCESS
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.net.DataResponse
import com.sc.timeline.repository.history.HistoricalRepositoryImpl
import com.sc.timeline.ui.HistoricalViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import timber.log.Timber

@RunWith(JUnit4::class)
class HistoricalViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var historicalRepository: HistoricalRepositoryImpl

    @MockK
    lateinit var context: Context

    lateinit var historicalViewModel: HistoricalViewModel

    val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        historicalViewModel = HistoricalViewModel(historicalRepository, context)
        historicalViewModel.mainDispacher = Dispatchers.Main
        historicalViewModel.ioDispacher = dispatcher
        Timber.i(historicalViewModel.toString())
    }

    @Test
    fun fetchData() {

        val response = DataResponse.success("", TIME_SERIES)

        coEvery { historicalRepository.showHistorical(any(), any(), TIME_SERIES) } returns response

        historicalViewModel.liveDataResponse.observeForever {}

        historicalViewModel.showHistorical("", "")

        assert(historicalViewModel.liveDataResponse.value != null)
        assert(historicalViewModel.liveDataResponse.value!!.status == SUCCESS)

    }

}