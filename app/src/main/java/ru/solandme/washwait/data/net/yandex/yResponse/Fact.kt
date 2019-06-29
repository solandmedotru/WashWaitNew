package ru.solandme.washwait.data.net.yandex.yResponse

data class Fact(
        val cloudness: Double,
        val condition: String,
        val daytime: String,
        val feels_like: Int,
        val humidity: Int,
        val icon: String,
        val obs_time: Int,
        val polar: Boolean,
        val prec_strength: Int,
        val prec_type: Int,
        val pressure_mm: Int,
        val pressure_pa: Int,
        val season: String,
        val soil_moisture: Double,
        val soil_temp: Int,
        val source: String,
        val temp: Int,
        val uv_index: Int,
        val wind_dir: String,
        val wind_gust: Int,
        val wind_speed: Double
)