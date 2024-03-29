package com.sc.core.api

import com.sc.core.CoreConstants.Companion.ENDPOINT_TIME_SERIES
import com.sc.core.CoreConstants.Companion.END_DATE_LABEL
import com.sc.core.CoreConstants.Companion.HEADER_ACCEPT_JSON
import com.sc.core.CoreConstants.Companion.HEADER_CONTENT_TYPE_JSON
import com.sc.core.CoreConstants.Companion.START_DATE_LABEL
import com.sc.core.model.remote.TimeSeriesRemote
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoneyApi {

    @Headers(HEADER_ACCEPT_JSON, HEADER_CONTENT_TYPE_JSON)
    @GET(ENDPOINT_TIME_SERIES)
    fun getMoneyTimeSeriesByDateAsync(
        @Query(START_DATE_LABEL) startDate: String,
        @Query(END_DATE_LABEL) endDate: String
    ):
            Deferred<TimeSeriesRemote>

}