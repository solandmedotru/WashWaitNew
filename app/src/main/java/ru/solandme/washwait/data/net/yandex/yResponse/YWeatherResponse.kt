package ru.solandme.washwait.data.net.yandex.yResponse

data class YWeatherResponse(
        val fact: Fact,
        val forecasts: List<Forecast>,
        val info: Info,
        val now: Int,
        val now_dt: String
)