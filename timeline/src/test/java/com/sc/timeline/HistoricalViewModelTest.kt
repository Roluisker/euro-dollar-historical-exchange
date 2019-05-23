package com.sc.timeline

import android.content.Context
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sc.core.CoreConstants.Companion.EUR
import com.sc.core.CoreConstants.Companion.JPY
import com.sc.core.CoreConstants.Companion.USD
import com.sc.core.DateUtilities
import com.sc.core.SIMPLE_TIME_FORMAT_DATE
import com.sc.core.annotation.net.SUCCESS
import com.sc.core.annotation.net.TIME_SERIES
import com.sc.core.model.local.TimeSeries

import com.sc.core.model.remote.TimeSeriesRemote
import com.sc.core.net.DataResponse
import com.sc.timeline.repository.history.HistoricalRepositoryImpl
import com.sc.timeline.ui.HistoricalViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass

import kotlinx.coroutines.Dispatchers
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import timber.log.Timber
import java.io.InputStream

@RunWith(JUnit4::class)
class HistoricalViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var historicalRepository: HistoricalRepositoryImpl

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var appContext: Context

    @MockK
    lateinit var resources: Resources

    @MockK
    lateinit var inputStream: InputStream

    @MockK
    lateinit var timeSeriesRemote: TimeSeriesRemote

    @MockK
    lateinit var timeSeries: TimeSeries

    //var ratesMap: HashMap<String, HashMap<String, String>> = HashMap()

    lateinit var historicalViewModel: HistoricalViewModel

    val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        historicalViewModel = HistoricalViewModel(historicalRepository, context, dispatcher, dispatcher)

        /*
        Context context = mock(Context.class);
        Context appContext = mock(Context.class);
        Resources resources = mock(Resources.class);
        when(resources.openRawResource(anyInt())).thenReturn(mock(InputStream.class));
        when(appContext.getResources()).thenReturn(resources);



        when(context.getApplicationContext()).thenReturn(appContext);
        JodaTimeAndroid.init(context);*/

        /*
        every { resources.openRawResource(any()) } returns inputStream
        every { appContext.resources } returns resources
        every { context.applicationContext } returns appContext

        JodaTimeAndroid.init(context)*/


        Timber.i(historicalViewModel.toString())

    }

    @Test
    fun fetchData() {

        val mapResult: HashMap<String, HashMap<String, String>> = hashMapOf(
            "2019-03-01" to
                    hashMapOf(
                        USD to "1.1",
                        JPY to "2",
                        EUR to "1"
                    )
        )

        every { timeSeriesRemote.rates.rateItem } returns mapResult

        val response = DataResponse.success(timeSeriesRemote, TIME_SERIES)

        coEvery { historicalRepository.showHistorical(any(), any(), TIME_SERIES) } returns response

        historicalViewModel.liveDataResponse.observeForever {}

        historicalViewModel.showHistorical("", "")

        assert(historicalViewModel.liveDataResponse.value != null)
        assert(historicalViewModel.liveDataResponse.value!!.status == SUCCESS)

    }


    /*

    val success: Boolean,
    val timeseries: String? = "",
    val start_date: String? = "",
    val end_date: String? = "",
    val base: String? = "",
    var rates: TimeSeries = TimeSeries()


    private fun timeSeriesRemote(
        success: Boolean, timeSeries: String,
        startDate: String, endDate: String,
        base: String, rateItem: HashMap<String, HashMap<String, String>> = HashMap()
    ): TimeSeriesRemote {
        return timeSeriesRemote(success, timeSeries, startDate, endDate, base, rateItem)
    } */

}