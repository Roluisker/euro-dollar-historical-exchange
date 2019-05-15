package com.sc.core.net

import com.sc.core.CoreConstants
import com.sc.core.annotation.ErrorTypes

data class ErrorData(
    @ErrorTypes val type: String = CoreConstants.EMPTY, val code: Int = -1,
    val message: String = CoreConstants.EMPTY,
    val request: String = CoreConstants.EMPTY) {

    override fun toString(): String {
        return if (code > 0) {
            "..> Error on request: ${request.toUpperCase()}, -> type: $type :{ code: $code, message: $message}"
        } else {
            "..> Error on request: ${request.toUpperCase()}, -> type: $type :{ message: $message} "
        }
    }
}

data class BasicError(
    val code: Int,
    val message: String
)