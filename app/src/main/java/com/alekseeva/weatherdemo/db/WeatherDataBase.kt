package com.alekseeva.weatherdemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alekseeva.weatherdemo.model.Weather

@Database(entities = [(Weather::class)], version = 1)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}