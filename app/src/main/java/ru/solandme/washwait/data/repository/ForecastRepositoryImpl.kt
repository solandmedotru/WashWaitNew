package ru.solandme.washwait.data.repository

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.solandme.washwait.data.db.WeatherDAO
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.net.WeatherNetworkDataSource


class ForecastRepositoryImpl(
        private val weatherDAO: WeatherDAO,
        private val weatherNetworkDataSource: WeatherNetworkDataSource
) : WeatherRepository {


    @UiThread
    override fun getForecastWeatherByCity(lang: String, location: String): LiveData<List<WeatherEntity>> {
        GlobalScope.launch(Dispatchers.IO) {
            val fetchForecastWeatherByCity = weatherNetworkDataSource.fetchForecastWeatherByCity("London", "metric","ru")
            weatherDAO.insertAll(fetchForecastWeatherByCity)
        }
        return weatherDAO.getWeathers()
    }

    override fun getCurrentWeatherByCity(lang: String, location: String): LiveData<WeatherEntity> {
        GlobalScope.launch(Dispatchers.IO) {
            val fetchCurrentWeatherByCity = weatherNetworkDataSource.fetchCurrentWeatherByCity("London", "metric","ru")
            weatherDAO.insert(fetchCurrentWeatherByCity)
        }
        return weatherDAO.getCurrentWeather()
    }
}

private fun isNeedUpdate(lastUpdatedTime: Long): Boolean {
    //TODO реализовать сравнение времени последней записи в базу с текущем временем
    return false
}