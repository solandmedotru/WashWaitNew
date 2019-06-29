package ru.solandme.washwait.data.net

import ru.solandme.washwait.data.db.entity.WeatherEntity

interface WeatherNetworkDataSource {

    suspend fun getForecastWeather(
            lat: String,
            lon: String,
            units: String,
            language: String
    ): List<WeatherEntity> {
        return emptyList()
    }

    suspend fun getForecastWeather(
            location: String,
            units: String,
            language: String
    ): List<WeatherEntity>{
        return emptyList()
    }

    suspend fun getForecastWeather(
            lat: String,
            lon: String,
            hours: String,
            language: String,
            limit: String,
            extra: String
    ): List<WeatherEntity>{
        return emptyList()
    }
}