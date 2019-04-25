package ru.solandme.washwait.data.net

import androidx.lifecycle.LiveData
import ru.solandme.washwait.data.net.OWCResponse.OWCurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<OWCurrentWeatherResponse>

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