package ru.solandme.washwait.data.repository

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.solandme.washwait.data.db.WeatherDAO
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.net.WeatherNetworkDataSource


class WeatherRepositoryImpl(
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
        return weatherDAO.getCurrentWeather()
    }

    private fun isNeedUpdate(lastUpdatedTime: Long): Boolean {
        //TODO реализовать сравнение времени последней записи в базу с текущем временем
            return false
    }
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