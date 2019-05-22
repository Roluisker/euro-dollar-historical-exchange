package com.sc.core.annotation.net

import androidx.annotation.StringDef

const val LOADING: String = "data_response_loading"
const val SUCCESS: String = "data_response_success"
const val ERROR: String = "data_response_error"
const val COMPLETED: String = "data_response_completed"

@Retention(AnnotationRetention.SOURCE)
@StringDef(LOADING, SUCCESS, ERROR, COMPLETED)
annotation class ResponseStatus