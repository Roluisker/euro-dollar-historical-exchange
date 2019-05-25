package com.sc.core

import com.sc.core.annotation.net.FixerRequest
import com.sc.core.net.DataResponse

open abstract class BaseRepository {
    abstract suspend fun errorHandler(error: Exception, @FixerRequest requestTag: String): DataResponse
}