package ru.solandme.washwait.data.net

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.solandme.washwait.data.db.entity.CurrentWeatherEntity
import ru.solandme.washwait.internal.NoConnectivityException
import ru.solandme.washwait.data.net.OWCResponse.OWCurrentWeatherResponse

class WeatherNetworkDataSourceImpl(
        private val openWeatherApiService: OpenWeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherEntity>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherEntity>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeatherByCity(location: String, language: String) {
        try {
            val fetchedCurrentWeather = openWeatherApiService
                    .getCurrentWeatherByCity(location, language)
                    .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    override suspend fun fetchCurrentWeatherByCoordinate(lat: String, lon: String, language: String) {
        try {
            val fetchedCurrentWeather = openWeatherApiService
                    .getCurrentWeatherByCoords(lat, lon, language)
                    .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}