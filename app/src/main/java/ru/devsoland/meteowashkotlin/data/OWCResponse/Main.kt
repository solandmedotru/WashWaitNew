package ru.devsoland.meteowashkotlin.data.OWCResponse

import com.google.gson.annotations.SerializedName

data class Main(
        val humidity: Int,
        val pressure: Float,
        val temp: Float,
        @SerializedName("temp_max")
        val tempMax: Float,
        @SerializedName("temp_min")
        val tempMin: Float
)