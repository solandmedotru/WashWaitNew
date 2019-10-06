package ru.solandme.washwait.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.weather_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.ui.forecast.ForecastWeatherViewModel
import ru.solandme.washwait.ui.forecast.ForecastWeatherViewModelFactory
import ru.solandme.washwait.ui.forecast.RWForecastAdapter
import kotlin.math.roundToInt

class WeatherFragment : Fragment(R.layout.weather_fragment), KodeinAware {
    override val kodein by kodein()
    private val currentWeatherViewModelFactory: CurrentWeatherViewModelFactory by instance()
    private val forecastWeatherViewModelFactory: ForecastWeatherViewModelFactory by instance()
    private lateinit var rwForecastAdapter: RWForecastAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initViewModels()
    }

    private fun initViews() {
        rwForecastAdapter = RWForecastAdapter {
            Snackbar.make(rwForecasts, "В этот день ${it.description}", Snackbar.LENGTH_SHORT).show() //TODO реализовать ответ, "В этот день лучше не мыть машину" или "В этот день можно мыть машину"
        }
        with(rwForecasts) {
            adapter = rwForecastAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val divider = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
            addItemDecoration(divider)
        }
    }

    private fun initViewModels() {
        val currentWeatherViewModel = ViewModelProvider(this, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)
        currentWeatherViewModel.getWeather().observe(this, Observer<WeatherEntity> {
            if (null != it) {
                fillWeatherCard(it)
            }
        })

        val forecastWeatherViewModel = ViewModelProvider(this, forecastWeatherViewModelFactory).get(ForecastWeatherViewModel::class.java)
        forecastWeatherViewModel.getForecastWeather().observe(this, Observer<List<WeatherEntity>> {
            if (null != it) {
                rwForecastAdapter.addItems(it)
            }
        })

        currentWeatherViewModel.getProgressState().observe(this, Observer<Boolean> {
            if (it) {
                progress_Bar.visibility = VISIBLE
            } else {
                progress_Bar.visibility = GONE
            }
        })
    }

    private fun fillWeatherCard(entity: WeatherEntity) {
        textTemp.text = entity.temp.toString() + "\u2103"
        textHumidity.text = entity.humidity.toString()
        textBarometer.text = entity.pressure.toString()
        textSpeedWind.text = entity.wind.speed.toString()
        textWindDir.text = getString(getWindRes(entity.wind.deg))
        weatherIcon.setImageResource(entity.icon)

        tw_city_name.text = entity.location.locationName

    }

    private fun getWindRes(direction: Double): Int {
        val dir = (direction % 360 / 45).roundToInt()
        when (dir % 16) {
            0 -> return R.string.wi_wind_north
            1 -> return R.string.wi_wind_north_east
            2 -> return R.string.wi_wind_east
            3 -> return R.string.wi_wind_south_east
            4 -> return R.string.wi_wind_south
            5 -> return R.string.wi_wind_south_west
            6 -> return R.string.wi_wind_west
            7 -> return R.string.wi_wind_north_west
            8 -> return R.string.wi_wind_north
        }
        return R.string.wi_wind_north
    }
}