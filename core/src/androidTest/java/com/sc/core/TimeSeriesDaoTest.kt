package com.sc.core

import android.content.Context

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sc.core.db.LydianLionDatabase
import com.sc.core.db.TimeSeriesDao
import com.sc.core.model.local.TimeSeries
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

const val FROM_GSON =
    "{\"2019-03-20\":{\"JPY\":\"126.462662\",\"USD\":\"1.142752\",\"EUR\":\"1\"},\"2019-03-10\":{\"JPY\":\"124.769527\",\"USD\":\"1.123205\",\"EUR\":\"1\"},\"2019-03-21\":{\"JPY\":\"126.042609\",\"USD\":\"1.137456\",\"EUR\":\"1\"},\"2019-03-11\":{\"JPY\":\"125.362839\",\"USD\":\"1.125885\",\"EUR\":\"1\"},\"2019-03-22\":{\"JPY\":\"124.440389\",\"USD\":\"1.132048\",\"EUR\":\"1\"},\"2019-03-12\":{\"JPY\":\"125.636449\",\"USD\":\"1.128713\",\"EUR\":\"1\"},\"2019-03-23\":{\"JPY\":\"124.440389\",\"USD\":\"1.132048\",\"EUR\":\"1\"},\"2019-03-13\":{\"JPY\":\"125.997247\",\"USD\":\"1.133074\",\"EUR\":\"1\"},\"2019-03-24\":{\"JPY\":\"124.348075\",\"USD\":\"1.129554\",\"EUR\":\"1\"},\"2019-03-14\":{\"JPY\":\"126.37775\",\"USD\":\"1.130806\",\"EUR\":\"1\"},\"2019-03-15\":{\"JPY\":\"126.406053\",\"USD\":\"1.134449\",\"EUR\":\"1\"},\"2019-03-05\":{\"JPY\":\"126.48783\",\"USD\":\"1.130608\",\"EUR\":\"1\"},\"2019-03-16\":{\"JPY\":\"126.406053\",\"USD\":\"1.134449\",\"EUR\":\"1\"},\"2019-03-06\":{\"JPY\":\"126.277156\",\"USD\":\"1.131247\",\"EUR\":\"1\"},\"2019-03-17\":{\"JPY\":\"126.284225\",\"USD\":\"1.131939\",\"EUR\":\"1\"},\"2019-03-07\":{\"JPY\":\"125.011787\",\"USD\":\"1.119796\",\"EUR\":\"1\"},\"2019-03-18\":{\"JPY\":\"126.256726\",\"USD\":\"1.133588\",\"EUR\":\"1\"},\"2019-03-08\":{\"JPY\":\"124.943788\",\"USD\":\"1.123949\",\"EUR\":\"1\"},\"2019-03-19\":{\"JPY\":\"126.496375\",\"USD\":\"1.135454\",\"EUR\":\"1\"},\"2019-03-09\":{\"JPY\":\"124.943788\",\"USD\":\"1.123949\",\"EUR\":\"1\"}}"

@RunWith(AndroidJUnit4::class)
class TimeSeriesDaoTest {

    private lateinit var timeSeriesDao: TimeSeriesDao
    private lateinit var lydianLionDatabase: LydianLionDatabase
    private val timeSeries = TimeSeries(
        "2019-05-01",
        "2019-05-23",
        FROM_GSON
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        lydianLionDatabase = Room.inMemoryDatabaseBuilder(
            context, LydianLionDatabase::class.java
        ).build()
        timeSeriesDao = lydianLionDatabase.timeSeriesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        lydianLionDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeTimeSeriesAndReadTest() = runBlocking {

        timeSeriesDao.insertSeries(timeSeries)

        val timeSeriesFromDb = timeSeriesDao.seriesByDates("2019-05-01", "2019-05-23")

        assertThat(timeSeriesFromDb.rateItemJson, equalTo(timeSeries.rateItemJson))

    }

    @Test
    @Throws(Exception::class)
    fun writeTimeSeriesAndDeleteTest() = runBlocking {

        timeSeriesDao.insertSeries(timeSeries)
        var result = timeSeriesDao.series()!!

        assertThat(result[0].rateItemJson, equalTo(timeSeries.rateItemJson))

        timeSeriesDao.dropSeries()

        var dropResult = timeSeriesDao.series()!!

        assert(dropResult.isEmpty())

    }

    @Test
    @Throws(Exception::class)
    fun addTimeSeriesTest() = runBlocking {

        timeSeriesDao.insertSeries(timeSeries)
        timeSeriesDao.insertSeries(timeSeries)

        var result = timeSeriesDao.series()!!

        assert(result.size == 2)

    }

}