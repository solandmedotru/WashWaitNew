package ru.solandme.washwait

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ru.solandme.washwait.data.db.ForecastDataBase
import ru.solandme.washwait.data.db.WeatherDAO
import ru.solandme.washwait.data.net.OpenWeatherApiService
import ru.solandme.washwait.data.net.WeatherNetworkDataSource
import ru.solandme.washwait.data.net.WeatherNetworkDataSourceImpl
import ru.solandme.washwait.data.net.interceptors.ConnectivityInterceptor
import ru.solandme.washwait.data.net.interceptors.ConnectivityInterceptorImpl
import ru.solandme.washwait.data.repository.WeatherRepository
import ru.solandme.washwait.data.repository.ForecastRepositoryImpl
import ru.solandme.washwait.ui.forecast.ForecastWeatherViewModelFactory
import ru.solandme.washwait.ui.weather.CurrentWeatherViewModelFactory

class WashWaitApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WashWaitApp))

        bind<ForecastDataBase>() with singleton { ForecastDataBase(instance()) }
        bind<WeatherDAO>() with singleton { instance<ForecastDataBase>().weatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<OpenWeatherApiService>() with singleton { OpenWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with  singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<WeatherRepository>() with  singleton { ForecastRepositoryImpl(instance(), instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
        bind() from provider { ForecastWeatherViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}