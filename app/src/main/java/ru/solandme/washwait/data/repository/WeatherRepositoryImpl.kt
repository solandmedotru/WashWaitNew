package ru.solandme.washwait.data.repository

import android.content.Context
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.WeatherDAO
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.data.net.WeatherNetworkDataSource


class ForecastRepositoryImpl(
        private val weatherDAO: WeatherDAO,
        private val weatherNetworkDataSource: WeatherNetworkDataSource,
        private val context: Context
) : WeatherRepository {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val languageFromPref = preferences.getString(context.resources.getString(R.string.pref_language_key), "en")
    private val cityFromPref = preferences.getString(context.resources.getString(R.string.pref_city_key), "Moscow")
    private val unitsFromPref = preferences.getString(context.resources.getString(R.string.pref_units_key), "metric")

    @UiThread
    override fun getForecastWeather(): LiveData<List<WeatherEntity>> {
        GlobalScope.launch(Dispatchers.IO) {
            val fetchForecastWeather = weatherNetworkDataSource.getForecastWeather(cityFromPref, unitsFromPref, languageFromPref)
            weatherDAO.insertAll(fetchForecastWeather)
        }
        return weatherDAO.getWeathers()
    }

    fun saveCity(city: String) {
        GlobalScope.launch(Dispatchers.IO) {
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