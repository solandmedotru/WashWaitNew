package ru.solandme.washwait.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.repository.WeatherRepository

class CurrentWeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private lateinit var weathers: LiveData<WeatherEntity>

    fun getWeather(): LiveData<WeatherEntity> {
        return repository.getCurrentWeatherByCity("ru", "London")
//        if (!::weathers.isInitialized) {
//            weathers = MutableLiveData()
//            val value = repository.getForecastWeatherByCity("ru", "London")
//            Log.e("MY3", value?.get(0)?.temp.toString())
//            weathers.postValue(value)
////            GlobalScope.launch(Dispatchers.Main) {
////                val weatherList: List<WeatherEntity> = async(Dispatchers.IO) {
////                    return@async repository.getForecastWeatherByCity("ru", "London").value!!
////                }.await()
////                weathers.postValue(weatherList)
////            }
//        }
//        return weathers
    }
}