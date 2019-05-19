package com.sc.core.net

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        Timber.i(
            String.format(
                "Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()
            )
        )

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        Timber.i(
            String.format(
                "Received response for %s in %.1fms%n%s %s",
                response.request().url(), (t2 - t1) / 1e6, response.headers(), response.message()
            )
        )

        return response
    }

}