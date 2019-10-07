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
import androidx.recyclerview.widget.SimpleItemAnimator
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
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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
        with(entity){
            textTemp.text = temp.toString() + "\u2103"
            textHumidity.text = humidity.toString()
            textBarometer.text = pressure.toString()
            textSpeedWind.text = wind.speed.toString()
            textWindDir.text = getString(getWindRes(wind.deg))
            weatherIcon.setImageResource(icon)
            tw_city_name.text = location.locationName
        }

        with(entity.advices){
            if (needGlasses) glasses_icon.visibility = VISIBLE  else glasses_icon.visibility = GONE
            if (needUmbrella) umbrella_icon.visibility = VISIBLE  else umbrella_icon.visibility = GONE
            if (needWash) car_wash_icon.visibility = VISIBLE  else car_wash_icon.visibility = GONE
            if (needLight) car_light_icon.visibility = VISIBLE  else car_light_icon.visibility = GONE
            if (needWear) hat_icon.visibility = VISIBLE  else hat_icon.visibility = GONE
        }
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