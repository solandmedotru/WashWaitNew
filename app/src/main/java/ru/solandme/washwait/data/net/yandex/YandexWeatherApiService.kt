package ru.solandme.washwait.data.net.yandex

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.solandme.washwait.data.net.yandex.yResponse.YWeatherResponse
import ru.solandme.washwait.data.net.interceptors.ConnectivityInterceptor

const val BASE_URL = "https://api.weather.yandex.ru/v1/"

interface YandexWeatherApiService {

    @Headers("X-Yandex-API-Key: 39a777ef-021e-4073-9767-5b139f242755")
    @GET("forecast")
    fun getForecastWeatherAsync(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("hours") hours: String = "false",
            @Query("lang") language: String = "ru-Ru",
            @Query("limit") limit: String = "7",
            @Query("extra") extra: String = "true"
            ): Deferred<Response<YWeatherResponse>>

    companion object {
        fun getWeatherApi(
                connectivityInterceptor: ConnectivityInterceptor
        ): YandexWeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
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
                    .create(YandexWeatherApiService::class.java)
        }
    }
}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}