package ru.solandme.washwait.data.net.owm

import android.util.Log
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.entity.Advices
import ru.solandme.washwait.data.db.entity.Location
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.db.entity.Wind
import ru.solandme.washwait.internal.NoConnectivityException
import ru.solandme.washwait.data.net.owm.owcResponse.OWCurrentWeatherResponse
import ru.solandme.washwait.data.net.owm.owfResponse.OWForecastResponse
import ru.solandme.washwait.data.net.WeatherNetworkDataSource
import ru.solandme.washwait.data.net.owm.owfResponse.X

class OWNetworkDataSourceImpl(
        private val openWeatherApiService: OpenWeatherApiService
) : WeatherNetworkDataSource {

    override suspend fun getForecastWeather(lat: String, lon: String, units: String, language: String): List<WeatherEntity> {
        var forecast: MutableList<WeatherEntity> = mutableListOf()
        try {
            val fetchedForecast = openWeatherApiService
                    .getForecastWeatherAsync(lat, lon, units, language)
                    .await()
            return if (fetchedForecast.isSuccessful) {
                forecast = forecastMapping(fetchedForecast.body()!!)
                forecast
            } else forecast
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
        return forecast
    }

    override suspend fun getForecastWeather(location: String, units: String, language: String): List<WeatherEntity> {
        var forecast: MutableList<WeatherEntity> = mutableListOf()
        try {
            val fetchedForecast = openWeatherApiService
                    .getForecastWeatherAsync(location, units, language)
                    .await()

            return if (fetchedForecast.isSuccessful) {
                forecast = forecastMapping(fetchedForecast.body()!!)
                forecast
            } else forecast
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
                    getAdvices(response.list[id].weather[0].id),
                    getWeatherPicture(response.list[id].weather[0].icon),
                    Location(response.city.lat, response.city.lon, response.city.name),
                    response.list[id].dt.toLong()
            ))
        }
        return forecast
    }

    private fun getAdvices(response: Int): Advices {
        var needGlasses = false
        var needUmbrella = false
        var needWash = false
        var needLight = false
        var needWear = false

        when (response) {
            in 200..531 -> {
                needUmbrella = true
                needWash = false
            }
            in 600..622 -> {
                needWear = true
                needWash = false
            }
            in 700..761 -> {
                needLight = true
            }
            800 -> {
                needGlasses = true
            }
        }
        return Advices(needGlasses, needUmbrella, needWash, needLight, needWear)

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
                getAdvices(fetchedCurrentWeather.id),
                getWeatherPicture(fetchedCurrentWeather.weather[id].icon),
                Location(fetchedCurrentWeather.coord.lat,
                        fetchedCurrentWeather.coord.lon,
                        fetchedCurrentWeather.name
                ),
                fetchedCurrentWeather.dt.toLong()
        )
    }

    private fun getWeatherPicture(icon: String): Int {
        when (icon) {
            "01d" -> return R.drawable.ic_weather_icons_02
            "01n" -> return R.drawable.ic_weather_icons_02
            "02d" -> return R.drawable.ic_weather_icons_05
            "02n" -> return R.drawable.ic_weather_icons_05
            "03d" -> return R.drawable.ic_weather_icons_03
            "03n" -> return R.drawable.ic_weather_icons_03
            "04d" -> return R.drawable.ic_weather_icons_04
            "04n" -> return R.drawable.ic_weather_icons_04
            "09d" -> return R.drawable.ic_weather_icons_12
            "09n" -> return R.drawable.ic_weather_icons_12
            "10d" -> return R.drawable.ic_weather_icons_08
            "10n" -> return R.drawable.ic_weather_icons_08
            "11d" -> return R.drawable.ic_weather_icons_06
            "11n" -> return R.drawable.ic_weather_icons_06
            "13d" -> return R.drawable.ic_weather_icons_10
            "13n" -> return R.drawable.ic_weather_icons_10
            "50d" -> return R.drawable.ic_weather_icons_09
            "50n" -> return R.drawable.ic_weather_icons_09
            else -> return R.drawable.ic_weather_icons_02
        }
    }
}