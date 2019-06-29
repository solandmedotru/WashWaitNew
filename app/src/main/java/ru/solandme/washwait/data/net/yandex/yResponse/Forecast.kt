package ru.solandme.washwait.data.net.yandex.yResponse

data class Forecast(
        val date: String,
        val date_ts: Int,
        val moon_code: Int,
        val moon_text: String,
        val parts: Parts,
        val rise_begin: String,
        val set_end: String,
        val sunrise: String,
        val sunset: String,
        val week: Int
)