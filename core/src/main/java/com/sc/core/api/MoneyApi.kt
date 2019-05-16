package com.sc.core.api

import com.sc.core.model.remote.TimeSeriesRemote
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val HEADER_ACCEPT_JSON: String = "Accept: application/json"
const val HEADER_CONTENT_TYPE_JSON: String = "Content-Type: application/json"

const val ENDPOINT_TIME_SERIES: String = "timeseries"
const val ENDPOINT_LATEST: String = "latest"

const val START_DATE_LABEL: String = "start_date"
const val END_DATE_LABEL: String = "end_date"

interface MoneyApi {

    @GET(ENDPOINT_LATEST)
    fun getLatest(): Deferred<Response<TimeSeriesRemote>>

    @Headers(HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE_JSON)
    @GET(ENDPOINT_TIME_SERIES)
    fun getMoneyTimeSeriesByDate(
        @Query(START_DATE_LABEL) startDate: String,
        @Query(END_DATE_LABEL) endDate: String
    ):
            Deferred<Response<TimeSeriesRemote>>

}