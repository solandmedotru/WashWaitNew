package ru.solandme.washwait.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.repository.WeatherRepository

class CurrentWeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private var showProgress: MutableLiveData<Boolean> = MutableLiveData()

    fun getWeather(): LiveData<WeatherEntity> {
        showProgress.postValue(true)
        return Transformations.map(repository.getCurrentWeatherByCity("ru", "London")){
            showProgress.postValue(false)
            return@map it
        }
    }

    fun getProgressState() : MutableLiveData<Boolean>{
        return showProgress
    }
}