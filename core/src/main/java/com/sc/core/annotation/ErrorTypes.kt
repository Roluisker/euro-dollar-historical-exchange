package com.sc.core.annotation

import androidx.annotation.StringDef

const val HTTP_ERROR: String = "http"
const val DISCONNECTED: String = "disconected"
const val UNEXPECTED: String = "unexpected"
const val NETWORK_ERROR: String = "network"

@Retention(AnnotationRetention.SOURCE)
@StringDef(HTTP_ERROR, NETWORK_ERROR, DISCONNECTED, UNEXPECTED)
annotation class ErrorTypes