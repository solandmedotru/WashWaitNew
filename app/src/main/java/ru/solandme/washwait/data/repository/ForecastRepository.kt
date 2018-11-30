package ru.solandme.washwait.data.repository

import androidx.lifecycle.LiveData
import ru.solandme.washwait.data.db.entity.CurrentWeatherEntity

interface ForecastRepository {
    suspend fun getCurrentWeatherByCity(lang: String, location: String): LiveData<CurrentWeatherEntity>
}