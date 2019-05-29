package ru.solandme.washwait.data.net.OWFResponse


data class OWForecastResponse(
        val city: City = City(),
        val cnt: Int = 0,
        val cod: String = "",
        val list: List<X> = listOf(),
        val message: Double = 0.0
)