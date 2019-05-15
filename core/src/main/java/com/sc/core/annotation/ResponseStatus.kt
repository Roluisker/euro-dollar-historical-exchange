package com.sc.core.annotation

import androidx.annotation.StringDef

const val ERROR: String = "data_response_error"
const val LOADING: String = "data_response_loading"
const val COMPLETED: String = "data_response_completed"
const val SUCCESS: String = "data_response_success"

@Retention(AnnotationRetention.SOURCE)
@StringDef(LOADING, SUCCESS, ERROR, COMPLETED)
annotation class ResponseStatus