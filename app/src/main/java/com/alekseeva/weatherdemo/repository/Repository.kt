package com.alekseeva.weatherdemo.repository

import android.content.Context
import com.alekseeva.weatherdemo.model.Weather
import com.alekseeva.weatherdemo.remote.RemoteDataSource
import com.alekseeva.weatherdemo.remote.toResultFlow
import com.alekseeva.weatherdemo.utils.NetWorkResult
import kotlinx.coroutines.flow.Flow

class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getWeather(context: Context, query:String): Flow<NetWorkResult<Weather>> {
        return toResultFlow(context){
            remoteDataSource.getWeather(query)
        }
    }
}

