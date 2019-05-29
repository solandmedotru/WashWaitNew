package ru.solandme.washwait.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.rw_forecast_item_row.*
import kotlinx.android.synthetic.main.weather_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.entity.WeatherEntity
import ru.solandme.washwait.ui.forecast.ForecastWeatherViewModel
import ru.solandme.washwait.ui.forecast.ForecastWeatherViewModelFactory
import ru.solandme.washwait.ui.forecast.RWForecastAdapter

class WeatherFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val currentWeatherViewModelFactory: CurrentWeatherViewModelFactory by instance()
    private val forecastWeatherViewModelFactory: ForecastWeatherViewModelFactory by instance()
    private val rwForecastAdapter by lazy {
        RWForecastAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rwForecasts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rwForecasts.adapter = rwForecastAdapter


        val currentWeatherViewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)
        currentWeatherViewModel.getWeather().observe(this, Observer<WeatherEntity> {
            if (null != it) {
                textTemp.text = it.temp.toString()+"\u2103"
                textHumidity.text = it.humidity.toString()
            }
        })

        val forecastWeatherViewModel = ViewModelProviders.of(this, forecastWeatherViewModelFactory).get(ForecastWeatherViewModel::class.java)
        forecastWeatherViewModel.getForecastWeather().observe(this, Observer<List<WeatherEntity>> {
            if (null != it) {
                rwForecastAdapter.addItems(it)
            }
        })
    }
}