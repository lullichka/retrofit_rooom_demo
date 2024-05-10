package com.alekseeva.weatherdemo.di

import com.alekseeva.weatherdemo.remote.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    factory { RemoteDataSource(get()) }
}