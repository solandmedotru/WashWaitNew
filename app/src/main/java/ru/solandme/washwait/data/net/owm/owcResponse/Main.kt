package ru.solandme.washwait.data.net.owm.owcResponse

import com.google.gson.annotations.SerializedName

data class Main(
        @SerializedName("grnd_level")
        val grndLevel: Double,
        val humidity: Int,
        val pressure: Double,
        @SerializedName("sea_level")
        val seaLevel: Double,
        val temp: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        @SerializedName("temp_min")
        val tempMin: Double
)