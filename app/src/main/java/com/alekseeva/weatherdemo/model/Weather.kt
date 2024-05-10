package com.alekseeva.weatherdemo.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "table_weather")
@Serializable
data class Weather(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @SerialName("current") @Embedded val current: Current? = null,
    @SerialName("condition") @Embedded val condition: Condition? = null,
)