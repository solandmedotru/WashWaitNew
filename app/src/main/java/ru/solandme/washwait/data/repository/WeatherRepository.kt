package ru.solandme.washwait.data.repository

import androidx.lifecycle.LiveData
import ru.solandme.washwait.data.db.entity.WeatherEntity

interface WeatherRepository {
    fun getForecastWeather(): LiveData<List<WeatherEntity>>
}
