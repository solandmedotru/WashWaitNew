package ru.solandme.washwait.data.net.yandex.yResponse

data class NightShort(
        val cloudness: Double,
        val condition: String,
        val feels_like: Int,
        val humidity: Int,
        val icon: String,
        val prec_mm: Double,
        val prec_prob: Int,
        val prec_strength: Double,
        val prec_type: Int,
        val pressure_mm: Int,
        val pressure_pa: Int,
        val soil_moisture: Double,
        val soil_temp: Int,
        val temp: Int,
        val wind_dir: String,
        val wind_gust: Double,
        val wind_speed: Double
)