package ru.solandme.washwait.data.net.yandex

import android.util.Log
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.entity.Location
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.db.entity.Wind
import ru.solandme.washwait.internal.NoConnectivityException
import ru.solandme.washwait.data.net.WeatherNetworkDataSource
import ru.solandme.washwait.data.net.yandex.yResponse.YWeatherResponse

class YWNetworkDataSourceImpl(
        private val yandexWeatherApiService: YandexWeatherApiService
) : WeatherNetworkDataSource {

    override suspend fun getForecastWeather(lat: String, lon: String, hours: String, language: String, limit: String, extra: String): List<WeatherEntity> {
        var forecast: MutableList<WeatherEntity> = mutableListOf()
        try {
            val fetchedForecast = yandexWeatherApiService
                    .getForecastWeatherAsync(lat, lon, hours, language, limit, extra)
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

    private fun forecastMapping(response: YWeatherResponse): MutableList<WeatherEntity> {
        val forecast: MutableList<WeatherEntity> = mutableListOf()
        response.forecasts.indices.forEach { id ->
            forecast.add(WeatherEntity(id,
                    response.forecasts[id].parts.day_short.humidity,
                    response.list[id].pressure.toInt(),
                    response.list[id].temp.day.toInt(),
                    response.list[id].temp.max.toInt(),
                    response.list[id].temp.min.toInt(),
                    Wind(response.list[id].deg, response.list[id].speed),
                    response.list[id].weather[0].description,
                    getWeatherPicture(response.list[id].weather[0].icon),
                    Location(response.city.lat, response.city.lon, response.city.name),
                    response.list[id].dt.toLong()
            ))
        }
        return forecast
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