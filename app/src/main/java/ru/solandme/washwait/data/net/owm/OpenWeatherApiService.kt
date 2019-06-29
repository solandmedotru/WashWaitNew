package ru.solandme.washwait.data.net.owm

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.solandme.washwait.data.net.owm.owcResponse.OWCurrentWeatherResponse
import ru.solandme.washwait.data.net.owm.owfResponse.OWForecastResponse
import ru.solandme.washwait.data.net.interceptors.ConnectivityInterceptor

const val API_KEY = "8c809aface8967d960a0bec0db127446"
const val BASE_URL = "https://api.openweathermap.org/data/2.5/"


interface OpenWeatherApiService {

    @GET("weather")
    fun getCurrentWeatherAsync(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("units") units: String = "metric",
            @Query("lang") language: String = "ru"): Deferred<Response<OWCurrentWeatherResponse>>

    @GET("weather")
    fun getCurrentWeatherAsync(
            @Query("q") location: String = "London",
            @Query("units") units: String = "metric",
            @Query("lang") language: String = "ru"): Deferred<Response<OWCurrentWeatherResponse>>

    @GET("forecast/daily")
    fun getForecastWeatherAsync(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("units") units: String = "metric",
            @Query("lang") language: String = "ru",
            @Query("cnt") cnt: String = "10"): Deferred<Response<OWForecastResponse>>

    @GET("forecast/daily")
    fun getForecastWeatherAsync(
            @Query("q") location: String = "London",
            @Query("units") units: String = "metric",
            @Query("lang") language: String = "ru",
            @Query("cnt") cnt: String = "10"): Deferred<Response<OWForecastResponse>>

    companion object {
        fun getWeatherApi(
                connectivityInterceptor: ConnectivityInterceptor
        ): OpenWeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("appId", API_KEY)
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(connectivityInterceptor)
                    .build()
            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(OpenWeatherApiService::class.java)
        }
    }
}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}