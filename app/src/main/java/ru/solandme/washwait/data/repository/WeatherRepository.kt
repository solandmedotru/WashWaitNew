package ru.solandme.washwait.data.repository

import androidx.lifecycle.LiveData
import ru.solandme.washwait.data.db.entity.WeatherEntity

interface WeatherRepository {
    fun getCurrentWeatherByCity(lang: String, location: String): LiveData<WeatherEntity>
    fun getForecastWeatherByCity(lang: String, location: String): LiveData<List<WeatherEntity>>
}
