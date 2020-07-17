package com.dynasty.dynastyscansapp.di

import com.dynasty.dynastyscansapp.BuildConfig
import com.dynasty.dynastyscansapp.data.api.ServiceApi
import com.dynasty.dynastyscansapp.data.repository.ServiceRepository
import com.dynasty.dynastyscansapp.data.repository.ServiceRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { provideOkHttpClient() }

    single { provideRetrofit(get()) }

    single { provideApiService(get()) }

}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

fun provideRetrofit(okHttp: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttp)
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun provideApiService(retrofit: Retrofit): ServiceApi =
    retrofit.create(ServiceApi::class.java)
