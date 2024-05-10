package com.alekseeva.weatherdemo

import android.app.Application
import com.alekseeva.weatherdemo.di.dataBaseModule
import com.alekseeva.weatherdemo.di.networkModule
import com.alekseeva.weatherdemo.di.remoteDataSourceModule
import com.alekseeva.weatherdemo.di.repositoryModule
import com.alekseeva.weatherdemo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            androidLogger()
            modules(networkModule, remoteDataSourceModule, repositoryModule, viewModelModule, dataBaseModule)
        }
    }
}