package com.sc.core.net

import com.sc.core.annotation.net.ERROR
import com.sc.core.annotation.net.LOADING
import com.sc.core.annotation.net.ResponseStatus
import com.sc.core.annotation.net.SUCCESS

const val ERROR_NOT_SET = -1

open class DataResponse(@ResponseStatus val status: String, var data: Any?, val error: Int, val request: String) {
    companion object {
        fun loading(request: String): DataResponse {
            return DataResponse(LOADING, null, ERROR_NOT_SET, request)
        }

        fun success(data: Any, request: String): DataResponse {
            return DataResponse(SUCCESS, data, ERROR_NOT_SET, request)
        }

        fun error(errorCode: Int, request: String): DataResponse {
            return DataResponse(ERROR, ERROR_NOT_SET, errorCode, request)
        }
    }

    fun clearData() {
        data = null
    }

}