package ru.solandme.washwait.data.net

import android.util.Log
import ru.solandme.washwait.data.db.entity.Location
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.db.entity.Wind
import ru.solandme.washwait.internal.NoConnectivityException
import ru.solandme.washwait.data.net.OWCResponse.OWCurrentWeatherResponse

class WeatherNetworkDataSourceImpl(
        private val openWeatherApiService: OpenWeatherApiService
) : WeatherNetworkDataSource {

    private val emptyWeatherEntity = WeatherEntity()

    override suspend fun fetchCurrentWeatherByCity(location: String, language: String): WeatherEntity {
        try {
            val fetchedCurrentWeather = openWeatherApiService
                    .getCurrentWeatherByCityAsync(location, language)
                    .await()
            return if (fetchedCurrentWeather.isSuccessful) {
                weatherMapping(fetchedCurrentWeather.body()!!)
            } else emptyWeatherEntity


        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
        return emptyWeatherEntity
    }

    override suspend fun fetchCurrentWeatherByCoordinate(lat: String, lon: String, language: String): WeatherEntity {
        try {
            val fetchedCurrentWeather = openWeatherApiService
                    .getCurrentWeatherByCityAsync(lat, lon, language)
                    .await()
            return if (fetchedCurrentWeather.isSuccessful) {
                weatherMapping(fetchedCurrentWeather.body()!!)
            } else emptyWeatherEntity


        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
        return emptyWeatherEntity
    }

    private fun weatherMapping(fetchedCurrentWeather: OWCurrentWeatherResponse): WeatherEntity {
        return WeatherEntity(
                0,
                fetchedCurrentWeather.main.humidity,
                fetchedCurrentWeather.main.pressure,
                fetchedCurrentWeather.main.temp,
                fetchedCurrentWeather.main.tempMax,
                fetchedCurrentWeather.main.tempMin,
                Wind(fetchedCurrentWeather.wind.deg, fetchedCurrentWeather.wind.speed),

                fetchedCurrentWeather.weather[0].description,
                fetchedCurrentWeather.weather[0].icon,
                Location(fetchedCurrentWeather.coord.lat,
                        fetchedCurrentWeather.coord.lon,
                        fetchedCurrentWeather.name
                ),
                fetchedCurrentWeather.dt.toLong()
        )
    }
}