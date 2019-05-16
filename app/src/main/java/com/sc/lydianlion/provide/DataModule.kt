package com.sc.lydianlion.provide

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sc.core.CoreConstants.Companion.OK_HTTP_CONNECT_TIME_OUT
import com.sc.core.CoreConstants.Companion.OK_HTTP_READ_TIME_OUT
import com.sc.core.CoreConstants.Companion.OK_HTTP_WRITE_TIME_OUT
import com.sc.core.api.MoneyApi
import com.sc.core.net.RequestInterceptor
import com.sc.lydianlion.BuildConfig
import com.sc.lydianlion.repository.history.HistoricalRepository
import com.sc.lydianlion.repository.history.HistoricalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Reusable
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
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
    fun provideOkHttpClient(interceptor: RequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(OK_HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(OK_HTTP_WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Reusable
    fun provideMovAneyi(retrofit: Retrofit): MoneyApi {
        return retrofit.create(MoneyApi::class.java)
    }

    @Provides
    @Reusable
    fun provideInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Provides
    @Singleton
    fun provideHistoricalRepository(moneyApi: MoneyApi): HistoricalRepository {
        return HistoricalRepositoryImpl(moneyApi)
    }

}