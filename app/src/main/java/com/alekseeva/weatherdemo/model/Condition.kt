package com.alekseeva.weatherdemo.model

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Condition(
    @SerialName("text") var text: String? = null,
    @SerialName("icon") var icon: String? = null,
)
