package ru.solandme.washwait.ui.forecast

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.entity.WeatherEntity


class ForecastFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()
    private val forecastWeatherViewModelFactory: ForecastWeatherViewModelFactory by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val forecastWeatherViewModel = ViewModelProviders.of(this, forecastWeatherViewModelFactory).get(ForecastWeatherViewModel::class.java)
        forecastWeatherViewModel.getForecastWeather().observe(this, Observer<List<WeatherEntity>> {
            if (null != it) {

            }
        })
    }
}