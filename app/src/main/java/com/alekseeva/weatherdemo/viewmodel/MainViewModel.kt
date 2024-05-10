package com.alekseeva.weatherdemo.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alekseeva.weatherdemo.db.WeatherDao
import com.alekseeva.weatherdemo.model.Weather
import com.alekseeva.weatherdemo.repository.Repository
import com.alekseeva.weatherdemo.utils.NetWorkResult
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository, application: Application, private val dao: WeatherDao) :
    BaseViewModel(application) {

    private val _responseWeather: MutableLiveData<NetWorkResult<Weather>> = MutableLiveData()
    val responseWeather: LiveData<NetWorkResult<Weather>> = _responseWeather
    private val _weather: MutableLiveData<Weather> = MutableLiveData()
    val weather: LiveData<Weather> = _weather

    fun getWeather(query: String) = viewModelScope.launch {
        repository.getWeather(context, query).collect { values ->
            _responseWeather.value = values
        }
    }

    fun insertWeatherIntoDb(weather: Weather) = viewModelScope.launch {
        dao.insertWeather(weather)
    }

    fun getWeatherFromDb() = viewModelScope.launch {
        dao.getWeather().collect { values ->
            _weather.value = values
        }
    }
}