package com.alekseeva.weatherdemo.remote

import com.alekseeva.weatherdemo.BuildConfig
import com.alekseeva.weatherdemo.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("key: ${BuildConfig.API_KEY}")
    @GET("current.json")
    suspend fun getWeather(@Query("q") query: String): Response<Weather>
}
