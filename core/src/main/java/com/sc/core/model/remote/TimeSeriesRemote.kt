package com.sc.core.model.remote

import com.google.gson.annotations.JsonAdapter
import com.sc.core.deserializer.TimeSeriesDeserializer
import com.sc.core.model.local.TimeSeries

@JsonAdapter(TimeSeriesDeserializer::class)
open class TimeSeriesRemote(
    val success: Boolean,
    val timeseries: String? = "",
    val start_date: String = "",
    val end_date: String = "",
    val base: String? = "",
    var rates: TimeSeries = TimeSeries(start_date, end_date)
)
