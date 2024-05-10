package com.alekseeva.weatherdemo.utils

import android.app.AlertDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.alekseeva.weatherdemo.R

const val API_INTERNET_MESSAGE = "No Internet Connection"

fun hasInternetConnection(context: Context?): Boolean {
    try {
        if (context == null)
            return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } catch (e: Exception) {
        return false
    }
}

fun showAlertDialog(context: Context, message: String) {
    try {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("OK") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    } catch (e: Exception) {
        e.stackTrace
    }
}

fun Geocoder.getAddress(
    latitude: Double,
    longitude: Double,
    address: (Address?) -> Unit
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latitude, longitude, 1) { address(it.firstOrNull()) }
        return
    }
    try {
        address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
    } catch (e: Exception) {
        address(null)
    }
}


