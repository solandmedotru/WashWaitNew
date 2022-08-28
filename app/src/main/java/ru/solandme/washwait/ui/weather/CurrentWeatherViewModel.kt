package ru.solandme.washwait.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.repository.WeatherRepository

class CurrentWeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private var progressBarMutable: MutableLiveData<Boolean> = MutableLiveData()
    val progressBar: LiveData<Boolean> = progressBarMutable

    fun getWeather(): LiveData<WeatherEntity> {
        progressBarMutable.postValue(true)
        return Transformations.map(repository.getCurrentWeather()) { weatherEntity ->
            progressBarMutable.postValue(false)
            return@map weatherEntity
        }
    }
}