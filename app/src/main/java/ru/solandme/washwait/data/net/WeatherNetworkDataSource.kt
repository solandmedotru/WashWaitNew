package ru.solandme.washwait.data.net

import androidx.lifecycle.LiveData
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.net.OWCResponse.OWCurrentWeatherResponse

interface WeatherNetworkDataSource {

    suspend fun fetchCurrentWeatherByCoordinate(
            lat: String,
            lon: String,
            language: String
    ): WeatherEntity

    suspend fun fetchCurrentWeatherByCity(
            location: String,
            language: String
    ): WeatherEntity
}