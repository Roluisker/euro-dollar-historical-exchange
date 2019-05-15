package com.sc.core.net

import com.sc.core.annotation.*

class DataResponse(@ResponseStatus val status: String, val data: Any?, val error: ErrorData?, val request: String?) {
    companion object {
        fun loading(): DataResponse {
            return DataResponse(LOADING, null, null, null)
        }

        fun success(data: Any, request: String?): DataResponse {
            return DataResponse(SUCCESS, data, null, request)
        }

        fun error(error: ErrorData, request: String?): DataResponse {
            return DataResponse(ERROR, null, error, request)
        }

        fun complete(): DataResponse {
            return DataResponse(COMPLETED, null, null, null)
        }
    }
}