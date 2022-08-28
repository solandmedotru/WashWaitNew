package ru.solandme.washwait.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.solandme.washwait.data.repository.WeatherRepository

@Suppress("UNCHECKED_CAST")
class ForecastWeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ForecastWeatherViewModel(repository) as T
    }
}
