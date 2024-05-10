package com.alekseeva.weatherdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.alekseeva.weatherdemo.databinding.ActivityMainBinding
import com.alekseeva.weatherdemo.model.Weather
import com.alekseeva.weatherdemo.utils.ApiResultHandler
import com.alekseeva.weatherdemo.utils.getAddress
import com.alekseeva.weatherdemo.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by inject()
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        permissionSetup()
        observeApiWeatherData()
        getWeatherFromDB()
        observeWeatherDBData()
    }

    private fun getWeatherAPI(city: String?) {
        city?.let {
            mainViewModel.getWeather(it)
        }
    }

    private fun observeApiWeatherData() {
        try {
            mainViewModel.responseWeather.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<Weather>(this@MainActivity, onLoading = {
                    showProgress(true)
                }, onSuccess = { data ->
                    showProgress(false)
                    data?.let { mainViewModel.insertWeatherIntoDb(it) }
                }, onFailure = {
                    showProgress(false)
                    mainViewModel.getWeatherFromDb()
                })
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun showProgress(isShown: Boolean) = if (isShown) activityMainBinding.progress.visibility =
        View.VISIBLE else activityMainBinding.progress.visibility = View.GONE

    private fun permissionSetup() {
        val permission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            setupLocation()
        }
    }

    @SuppressLint("MissingPermission")
    fun setupLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                val geocoder = Geocoder(this)
                location?.let {
                    geocoder.getAddress(location.latitude, location.longitude) { address: Address? ->
                        address?.let {
                            getWeatherAPI(address.postalCode)
                        }
                    }
                }
            }
    }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        when (it) {
            true -> {
                setupLocation()
            }

            false -> {
                showProgress(false)
            }
        }
    }

    private fun getWeatherFromDB() {
        mainViewModel.getWeatherFromDb()
    }

    private fun observeWeatherDBData() {
        try {
            mainViewModel.weather.observe(this) { data ->
                data?.let { activityMainBinding.tvTemperature.text = it.current?.tempC.toString() }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}