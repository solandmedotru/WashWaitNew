package ru.solandme.washwait.data.net

import androidx.lifecycle.LiveData
import ru.solandme.washwait.data.db.entity.CurrentWeatherEntity

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherEntity>

    suspend fun fetchCurrentWeatherByCoordinate(
            lat: String,
            lon: String,
            language: String
    )

    suspend fun fetchCurrentWeatherByCity(
            location: String,
            language: String
    )
}