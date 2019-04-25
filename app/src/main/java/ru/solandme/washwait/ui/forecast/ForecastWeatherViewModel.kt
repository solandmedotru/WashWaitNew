package ru.solandme.washwait.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.repository.WeatherRepository

class ForecastWeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    fun getForecastWeather(): LiveData<List<WeatherEntity>> {
        return repository.getForecastWeatherByCity("ru", "London")
    }
}