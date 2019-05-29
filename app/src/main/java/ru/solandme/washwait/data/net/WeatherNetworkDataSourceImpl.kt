package ru.solandme.washwait.data.net

import android.util.Log
import ru.solandme.washwait.data.db.entity.Location
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.db.entity.Wind
import ru.solandme.washwait.internal.NoConnectivityException
import ru.solandme.washwait.data.net.OWCResponse.OWCurrentWeatherResponse
import ru.solandme.washwait.data.net.OWFResponse.OWForecastResponse

class WeatherNetworkDataSourceImpl(
        private val openWeatherApiService: OpenWeatherApiService
) : WeatherNetworkDataSource {

    private val emptyWeatherEntity = WeatherEntity()

    override suspend fun fetchCurrentWeatherByCity(location: String, units: String, language: String): WeatherEntity {
        try {
            val fetchedCurrentWeather = openWeatherApiService
                    .getCurrentWeatherByCityAsync(location, units, language)
                    .await()
            return if (fetchedCurrentWeather.isSuccessful) {
                weatherMapping(0, fetchedCurrentWeather.body()!!)
            } else emptyWeatherEntity
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
        return emptyWeatherEntity
    }

    override suspend fun fetchCurrentWeatherByCoordinate(lat: String, lon: String, units: String, language: String): WeatherEntity {
        try {
            val fetchedCurrentWeather = openWeatherApiService
                    .getCurrentWeatherByCoordinatesAsync(lat, lon, units, language)
                    .await()
            return if (fetchedCurrentWeather.isSuccessful) {
                weatherMapping(0, fetchedCurrentWeather.body()!!)
            } else emptyWeatherEntity
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
        return emptyWeatherEntity
    }

    override suspend fun fetchForecastWeatherByCoordinate(lat: String, lon: String, units: String, language: String): List<WeatherEntity> {
        var forecast: MutableList<WeatherEntity> = mutableListOf()
        try {
            val fetchedForecast = openWeatherApiService
                    .getForecastWeatherByCoordinatesAsync(lat, lon, units, language)
                    .await()
            if (fetchedForecast.isSuccessful) {
                forecast = forecastMapping(fetchedForecast.body()!!)
                return forecast
            } else return forecast
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
        return forecast
    }

    override suspend fun fetchForecastWeatherByCity(location: String, units: String, language: String): List<WeatherEntity> {
        var forecast: MutableList<WeatherEntity> = mutableListOf()
        try {
            val fetchedForecast = openWeatherApiService
                    .getForecastWeatherByCityAsync(location, units, language)
                    .await()
            if (fetchedForecast.isSuccessful) {
                forecast = forecastMapping(fetchedForecast.body()!!)
                return forecast
            } else return forecast
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
        return forecast
    }

    private fun forecastMapping(response: OWForecastResponse): MutableList<WeatherEntity> {
        val forecast: MutableList<WeatherEntity> = mutableListOf()
        response.list.indices.forEach { id ->
            forecast.add(WeatherEntity(
                    id,
                    response.list[id].humidity,
                    response.list[id].pressure.toInt(),
                    response.list[id].temp.day.toInt(),
                    response.list[id].temp.max.toInt(),
                    response.list[id].temp.min.toInt(),
                    Wind(response.list[id].deg, response.list[id].speed),
                    response.list[id].weather[0].description,
                    response.list[id].weather[0].icon,
                    Location(response.city.lat, response.city.lon, response.city.name),
                    response.list[id].dt.toLong()
            ))
        }
        return forecast
    }


    private fun weatherMapping(id: Int, fetchedCurrentWeather: OWCurrentWeatherResponse): WeatherEntity {
        return WeatherEntity(
                id,
                fetchedCurrentWeather.main.humidity,
                fetchedCurrentWeather.main.pressure.toInt(),
                fetchedCurrentWeather.main.temp.toInt(),
                fetchedCurrentWeather.main.tempMax.toInt(),
                fetchedCurrentWeather.main.tempMin.toInt(),
                Wind(fetchedCurrentWeather.wind.deg, fetchedCurrentWeather.wind.speed),

                fetchedCurrentWeather.weather[id].description,
                fetchedCurrentWeather.weather[id].icon,
                Location(fetchedCurrentWeather.coord.lat,
                        fetchedCurrentWeather.coord.lon,
                        fetchedCurrentWeather.name
                ),
                fetchedCurrentWeather.dt.toLong()
        )
    }
}