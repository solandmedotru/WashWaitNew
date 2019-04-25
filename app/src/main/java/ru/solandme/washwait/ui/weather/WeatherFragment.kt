package ru.solandme.washwait.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.weather_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.entity.WeatherEntity

class WeatherFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()
    private val currentWeatherViewModelFactory: CurrentWeatherViewModelFactory by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val currentWeatherViewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)
        currentWeatherViewModel.getWeather().observe(this, Observer<WeatherEntity> {
            if (null != it) {
                textTemp.text = it.temp.toString()
                textHumidity.text = it.humidity.toString()
            }
        })
    }
}