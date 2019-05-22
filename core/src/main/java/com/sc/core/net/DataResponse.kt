package com.sc.core.net

import com.sc.core.annotation.net.ERROR
import com.sc.core.annotation.net.LOADING
import com.sc.core.annotation.net.ResponseStatus
import com.sc.core.annotation.net.SUCCESS

const val ERROR_NOT_SET = -1

class NuevoDataResponse (@ResponseStatus val status: String, var data: Any?, val error: Int, val request: String) {
    companion object {
        fun loading(request: String): NuevoDataResponse {
            return NuevoDataResponse(LOADING, null, ERROR_NOT_SET, request)
        }

        fun success(data: Any, request: String): NuevoDataResponse {
            return NuevoDataResponse(SUCCESS, data, ERROR_NOT_SET, request)
        }

        fun error(errorCode: Int, request: String): NuevoDataResponse {
            return NuevoDataResponse(ERROR, ERROR_NOT_SET, errorCode, request)
        }
    }
}