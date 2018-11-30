package ru.solandme.washwait.data.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.solandme.washwait.data.net.OWCResponse.OWCurrentWeatherResponse
import ru.solandme.washwait.data.net.interceptors.ConnectivityInterceptor

const val API_KEY = "8c809aface8967d960a0bec0db127446"

interface OpenWeatherApiService {

    @GET("weather")
    fun getCurrentWeatherByCoords(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("units") units: String = "metric",
            @Query("lang") language: String = "en"): Deferred<OWCurrentWeatherResponse>

    @GET("weather")
    fun getCurrentWeatherByCity(
            @Query("q") location: String = "London",
            @Query("units") units: String = "metric",
            @Query("lang") language: String = "en"): Deferred<OWCurrentWeatherResponse>

    companion object {
        operator fun invoke(
                connectivityInterceptor: ConnectivityInterceptor
        ): OpenWeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("appid", API_KEY)
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
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(OpenWeatherApiService::class.java)
        }
    }
}