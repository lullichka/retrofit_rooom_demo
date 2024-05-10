package com.alekseeva.weatherdemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alekseeva.weatherdemo.model.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

    @Query("SELECT * FROM table_weather")
    fun getWeather(): Flow<Weather>
}