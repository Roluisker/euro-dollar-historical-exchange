package com.sc.lydianlion.core.model.remote

import com.google.gson.annotations.JsonAdapter
import com.sc.lydianlion.core.deserializer.TimeSeriesDeserializer
import com.sc.lydianlion.core.model.local.TimeSeries

@JsonAdapter(TimeSeriesDeserializer::class)
data class TimeSeriesRemote(
    val success: Boolean,
    val timeseries: String? = "",
    val start_date: String? = "",
    val end_date: String? = "",
    val base: String,
    val rates: TimeSeries = TimeSeries()
)
