package ru.solandme.washwait.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.solandme.washwait.data.db.CurrentWeatherDAO
import ru.solandme.washwait.data.db.entity.CurrentWeatherEntity
import ru.solandme.washwait.data.net.WeatherNetworkDataSource


class ForecastRepositoryImpl(
        private val currentWeatherDAO: CurrentWeatherDAO,
        private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    override suspend fun getCurrentWeatherByCity(lang: String, location: String): LiveData<CurrentWeatherEntity> {
        val downloadedCurrentWeather = MutableLiveData<CurrentWeatherEntity>()

        var weather: LiveData<CurrentWeatherEntity>
        GlobalScope.launch(Dispatchers.IO) {
            weather = currentWeatherDAO.getWeather()
            weather.observeForever {
                if (isNeedUpdate(it.lastUpdate)) {
                    var value = weatherNetworkDataSource.downloadedCurrentWeather.value
                    if (null != value) currentWeatherDAO.upsert(value)
                }
            }
        }
        return weather
    }
}

private fun isNeedUpdate(lastUpdatedTime: Long): Boolean {
    //TODO реализовать сравнение времени последней записи в базу с текущем временем
    return true
}


//init {
//        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{
//            newCurrentWeather->persistFetchedCurrentWeather(newCurrentWeather)
//        }
//}

//    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherEntity){
//        GlobalScope.launch(Dispatchers.IO) {
//            currentWeatherDAO.upsert(fetchedWeather.)
//        }
//    }
//}