package com.sc.core

import com.sc.core.net.DataResponse
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result: DataResponse<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is DataResponse.Success ->
                data = result.data
            is DataResponse.Error -> {
                Timber.d(result.exception)
            }
        }

        return data

    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): DataResponse<T> {
        val response = call.invoke()
        if (response.isSuccessful) return DataResponse.Success(response.body()!!)
        return DataResponse.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }

}