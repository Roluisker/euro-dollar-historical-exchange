package com.sc.core.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sc.core.BuildConfig
import com.sc.core.CoreConstants
import com.sc.core.api.MoneyApi
import com.sc.core.net.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class CoreModule {

    @Provides
    @Reusable
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Reusable
    fun provideGson(): Gson {
        val builder = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Reusable
    fun provideOkHttpClient(
        interceptor: RequestInterceptor,
        apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(CoreConstants.OK_HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(CoreConstants.OK_HTTP_WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(CoreConstants.OK_HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Reusable
    fun provideMoneyApi(retrofit: Retrofit): MoneyApi {
        return retrofit.create(MoneyApi::class.java)
    }

    @Provides
    @Reusable
    fun provideInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Provides
    @Reusable
    fun provideRequestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newUrl = chain.request().url()
                .newBuilder()
                .addQueryParameter(CoreConstants.API_KEY_LABEL, BuildConfig.API_KEY)
                .build()
            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
    }

}