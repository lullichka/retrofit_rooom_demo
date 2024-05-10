package com.alekseeva.weatherdemo.di

import com.alekseeva.weatherdemo.remote.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

const val BASE_URL_GET = "https://api.weatherapi.com/v1/"
const val MEDIA_TYPE = "application/json"

private val json = Json { ignoreUnknownKeys = true }

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL_GET)
        .addConverterFactory(json.asConverterFactory(MEDIA_TYPE.toMediaType()))
        .build()
}

fun provideService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

val networkModule = module {
    single { provideRetrofit() }
    single { provideService(get()) }
}