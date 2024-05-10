package com.alekseeva.weatherdemo.remote

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getWeather(query: String) = apiService.getWeather(query)
}