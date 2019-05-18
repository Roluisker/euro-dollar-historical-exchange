package com.sc.lydianlion.core

import com.sc.lydianlion.core.net.DataResponse
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String,
        requestTag: String
    ): DataResponse<T>? {
        return safeApiResult(call, errorMessage, requestTag)
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String,
        requestTag: String
    ): DataResponse<T> {
        val response = call.invoke()
        if (response.isSuccessful) return DataResponse.Success(response.body()!!, requestTag)
        return DataResponse.Error(
            IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"),
            requestTag
        )
    }

}