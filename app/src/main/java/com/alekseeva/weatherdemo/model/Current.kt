package com.alekseeva.weatherdemo.model

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Current(@SerialName("temp_c") var tempC: Double? = null)