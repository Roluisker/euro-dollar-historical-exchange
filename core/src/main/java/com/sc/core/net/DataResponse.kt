package com.sc.core.net

import com.sc.core.annotation.net.ERROR
import com.sc.core.annotation.net.LOADING
import com.sc.core.annotation.net.ResponseStatus
import com.sc.core.annotation.net.SUCCESS

const val ERROR_NOT_SET = -1

const val RESPONSE_FROM_LOCAL = 3
const val RESPONSE_FROM_REMOTE = 4

open class DataResponse(
    @ResponseStatus val status: String,
    var data: Any?,
    val error: Int,
    val request: String,
    var responseFrom: Int = RESPONSE_FROM_REMOTE
) {

    companion object {
        fun loading(request: String): DataResponse = DataResponse(LOADING, null, ERROR_NOT_SET, request)

        fun success(data: Any, request: String): DataResponse = DataResponse(SUCCESS, data, ERROR_NOT_SET, request)

        fun successFromLocal(data: Any, request: String): DataResponse =
            DataResponse(SUCCESS, data, ERROR_NOT_SET, request, RESPONSE_FROM_LOCAL)

        fun error(errorCode: Int, request: String): DataResponse =
            DataResponse(ERROR, ERROR_NOT_SET, errorCode, request)
    }

    fun clearData() {
        data = null
    }

}