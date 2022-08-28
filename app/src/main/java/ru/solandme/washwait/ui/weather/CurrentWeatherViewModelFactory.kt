package ru.solandme.washwait.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.solandme.washwait.data.repository.WeatherRepository

@Suppress("UNCHECKED_CAST")
class CurrentWeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(repository = repository) as T
    }
}
