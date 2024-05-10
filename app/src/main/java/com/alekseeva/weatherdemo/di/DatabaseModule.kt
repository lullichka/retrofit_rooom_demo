package com.alekseeva.weatherdemo.di

import android.app.Application
import androidx.room.Room
import com.alekseeva.weatherdemo.db.WeatherDao
import com.alekseeva.weatherdemo.db.WeatherDataBase
import org.koin.dsl.module

fun provideDataBase(application: Application): WeatherDataBase =
    Room.databaseBuilder(
        application,
        WeatherDataBase::class.java,
        "table_weather"
    ).fallbackToDestructiveMigration().build()

fun provideDao(weatherDataBase: WeatherDataBase): WeatherDao = weatherDataBase.getWeatherDao()


val dataBaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}