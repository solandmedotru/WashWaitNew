package ru.solandme.washwait.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.solandme.washwait.R
import ru.solandme.washwait.data.net.interceptors.ConnectivityInterceptorImpl
import ru.solandme.washwait.data.net.OpenWeatherApiService
import ru.solandme.washwait.data.net.WeatherNetworkDataSourceImpl

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        val apiService = OpenWeatherApiService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)

        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
            textTemp.text = it.main.temp.toString()
        })


        //TODO: Сделано для проверки работы. Исправить.
        GlobalScope.launch(Dispatchers.Main) {
            weatherNetworkDataSource.fetchCurrentWeatherByCity("Moscow", "ru")
        }
    }
}