package com.alekseeva.weatherdemo.remote

import android.content.Context
import com.alekseeva.weatherdemo.utils.API_INTERNET_MESSAGE
import com.alekseeva.weatherdemo.utils.NetWorkResult
import com.alekseeva.weatherdemo.utils.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

inline fun <reified T> toResultFlow(context: Context, crossinline call: suspend () -> Response<T>): Flow<NetWorkResult<T>> {
    return flow {
        val isInternetConnected = hasInternetConnection(context)
        if (isInternetConnected) {
            emit(NetWorkResult.Loading(true))
            try {
                val c = call()
                if (c.isSuccessful && c.body() != null) {
                    emit(NetWorkResult.Success(c.body()))
                } else {
                    emit(NetWorkResult.Error(c.message()))
                }
            } catch (e: Exception) {
                emit(NetWorkResult.Error(e.toString()))
            }
        } else {
            emit(NetWorkResult.Error(API_INTERNET_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)
}

