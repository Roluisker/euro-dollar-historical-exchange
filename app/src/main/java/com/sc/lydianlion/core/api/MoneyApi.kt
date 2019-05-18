package com.sc.lydianlion.core.api

import com.sc.lydianlion.core.CoreConstants.Companion.ENDPOINT_LATEST
import com.sc.lydianlion.core.CoreConstants.Companion.ENDPOINT_TIME_SERIES
import com.sc.lydianlion.core.CoreConstants.Companion.END_DATE_LABEL
import com.sc.lydianlion.core.CoreConstants.Companion.HEADER_ACCEPT_JSON
import com.sc.lydianlion.core.CoreConstants.Companion.HEADER_CONTENT_TYPE_JSON
import com.sc.lydianlion.core.CoreConstants.Companion.START_DATE_LABEL
import com.sc.lydianlion.core.model.remote.TimeSeriesRemote
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoneyApi {

    @GET(ENDPOINT_LATEST)
    fun getLatest(): Deferred<Response<TimeSeriesRemote>>

    @Headers(HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE_JSON)
    @GET(ENDPOINT_TIME_SERIES)
    fun getMoneyTimeSeriesByDateAsync(
        @Query(START_DATE_LABEL) startDate: String,
        @Query(END_DATE_LABEL) endDate: String
    ):
            Deferred<Response<TimeSeriesRemote>>

}