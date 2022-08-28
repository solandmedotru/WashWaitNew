package ru.solandme.washwait.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import kotlinx.coroutines.*
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.WeatherDAO
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.net.WeatherNetworkDataSource
import ru.solandme.washwait.data.net.owm.OWNetworkDataSourceImpl


class WeatherRepositoryImpl(
        private val weatherDAO: WeatherDAO,
        private val context: Context,
        private val owNetworkDataSourceImpl: OWNetworkDataSourceImpl
) : WeatherRepository {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val languageFromPref = preferences.getString(context.resources.getString(R.string.pref_language_key), "en")
    private val cityFromPref = preferences.getString(context.resources.getString(R.string.pref_city_key), "Moscow")
    private val unitsFromPref = preferences.getString(context.resources.getString(R.string.pref_units_key), "metric")
    private lateinit var weatherNetworkDataSource: WeatherNetworkDataSource

    override fun getForecastWeather(): LiveData<List<WeatherEntity>> {
        weatherNetworkDataSource = owNetworkDataSourceImpl //TODO реализовать выбор проввайдера на основе сохраненных настроек

        CoroutineScope(Dispatchers.IO).launch {
            val fetchForecastWeather = withContext(Dispatchers.IO) {
                weatherNetworkDataSource.getForecastWeather(cityFromPref, unitsFromPref, languageFromPref)
            }
            weatherDAO.insertAll(fetchForecastWeather)
        }
        return weatherDAO.getWeathers()
    }

    override fun getCurrentWeather(): LiveData<WeatherEntity> {
        return weatherDAO.getCurrentWeather()
    }

    fun saveCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val editor = preferences.edit()
            editor.putString(context.resources.getString(R.string.pref_city_key), city)
            editor.apply()
        }
    }
}

private fun isNeedUpdate(lastUpdatedTime: Long): Boolean {
    //TODO реализовать сравнение времени последней записи в базу с текущем временем
    return false
}