package ru.solandme.washwait.data.net.yandex.yResponse

data class DayShort(
        val _source: String,
        val cloudness: Double,
        val condition: String,
        val feels_like: Int,
        val humidity: Int,
        val icon: String,
        val prec_mm: Int,
        val prec_prob: Int,
        val prec_strength: Int,
        val prec_type: Int,
        val pressure_mm: Int,
        val pressure_pa: Int,
        val soil_moisture: Double,
        val soil_temp: Int,
        val temp: Int,
        val temp_min: Int,
        val wind_dir: String,
        val wind_gust: Double,
        val wind_speed: Int
)