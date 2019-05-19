package com.sc.core.annotation.net

import androidx.annotation.StringDef

const val TIME_SERIES: String = "time_series_info"

@Retention(AnnotationRetention.SOURCE)
@StringDef(TIME_SERIES)
annotation class FixerRequest