package com.sc.core

class CoreConstants {
    companion object {
        const val EMPTY = ""
        const val API_KEY_LABEL = "access_key"
        const val OK_HTTP_CONNECT_TIME_OUT = 10L
        const val OK_HTTP_WRITE_TIME_OUT = 10L
        const val OK_HTTP_READ_TIME_OUT = 30L
        const val ENDPOINT_TIME_SERIES: String = "timeseries"
        const val ENDPOINT_LATEST: String = "latest"
        const val START_DATE_LABEL: String = "start_date"
        const val END_DATE_LABEL: String = "end_date"
        const val SUCCESS_LABEL = "success"
        const val TIME_SERIES_LABEL = "timeseries"
        const val BASE_LABEL = "base"
        const val RATE_LABEL = "rates"
        const val HEADER_ACCEPT_JSON: String = "Accept: application/json"
        const val HEADER_CONTENT_TYPE_JSON: String = "Content-Type: application/json"
    }
}