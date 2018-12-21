package com.example.bogdanov.repastapp.di

import com.example.bogdanov.repastapp.BuildConfig
import com.example.bogdanov.repastapp.data.ApiService
import com.google.gson.ExclusionStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {

    companion object {
        private const val CONNECT_TIMEOUT = 20L
        private const val READ_TIMEOUT = 20L
        private const val WRITE_TIMEOUT = 20L

        const val DEFAULT_API_HOST = "http://cookit.zenithapps.ru/"
    }

    @Provides
    internal fun provideNetworkInterceptors(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor)
            : OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
        return okHttpClientBuilder
            .build()
    }

    @Singleton
    @Provides
    internal fun provideGson(): Gson = GsonBuilder()
        .create()

    @Singleton
    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(DEFAULT_API_HOST)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}