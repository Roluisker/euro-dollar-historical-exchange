package com.sc.core.net

sealed class DataResponse<out T : Any> {
    data class Success<out T : Any>(val data: T, val requestTag: String) : DataResponse<T>()
    data class Error(val exception: Exception, val requestTag: String) : DataResponse<Nothing>()
}