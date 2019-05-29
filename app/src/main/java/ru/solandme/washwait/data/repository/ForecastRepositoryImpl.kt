package ru.solandme.washwait.data.repository

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.solandme.washwait.data.db.WeatherDAO
import ru.solandme.washwait.data.db.entity.Location
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.db.entity.Wind
import ru.solandme.washwait.data.net.WeatherNetworkDataSource


class ForecastRepositoryImpl(
        private val weatherDAO: WeatherDAO,
        private val weatherNetworkDataSource: WeatherNetworkDataSource
) : WeatherRepository {


    @UiThread
    override fun getForecastWeatherByCity(lang: String, location: String): LiveData<List<WeatherEntity>> {
        var forecastWeather: MutableLiveData<List<WeatherEntity>> = MutableLiveData()

//        GlobalScope.launch(Dispatchers.Main) {
//            val forecast: LiveData<List<WeatherEntity>> = async(Dispatchers.IO) {
//                Log.e("MY1", "Add to DB")
//                val w = weatherDAO.getWeathers()
//                Log.e("MY2", w.toString())
//                return@async w
//            }.await()
//            forecastWeather = forecast as MutableLiveData<List<WeatherEntity>>
//        }
        val w = weatherDAO.getWeathers()
        Log.e("MY2", w.toString())
        return w
    }

    override fun getCurrentWeatherByCity(lang: String, location: String): LiveData<WeatherEntity> {
        GlobalScope.launch(Dispatchers.IO) {
            delay(5000) //TODO удалить имитацию запроса в сеть
            val weatherEntity: WeatherEntity = WeatherEntity(
                    0,
                    20,
                    10.0,
                    20.0,
                    10.0,
                    10.0,
                    Wind(10.0,10.0),
                    "",
                    "",
                    Location(10.0,10.0),
                    10L)
            weatherDAO.insert(weatherEntity)
            val weatherEntity1: WeatherEntity = WeatherEntity(
                    1,
                    10,
                    10.0,
                    10.0,
                    10.0,
                    10.0,
                    Wind(10.0,10.0),
                    "",
                    "",
                    Location(10.0,10.0),
                    10L)
            weatherDAO.insert(weatherEntity1)
            val weatherEntity2: WeatherEntity = WeatherEntity(
                    2,
                    10,
                    10.0,
                    10.0,
                    10.0,
                    10.0,
                    Wind(10.0,10.0),
                    "",
                    "",
                    Location(10.0,10.0),
                    10L)
            weatherDAO.insert(weatherEntity2)
            val weatherEntity3: WeatherEntity = WeatherEntity(
                    3,
                    10,
                    10.0,
                    10.0,
                    10.0,
                    10.0,
                    Wind(10.0,10.0),
                    "",
                    "",
                    Location(10.0,10.0),
                    10L)
            weatherDAO.insert(weatherEntity3)
       }

        return weatherDAO.getCurrentWeather()
    }
}

private fun isNeedUpdate(lastUpdatedTime: Long): Boolean {
    //TODO реализовать сравнение времени последней записи в базу с текущем временем
    return false
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