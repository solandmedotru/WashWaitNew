package ru.solandme.washwait.data.net.OWFResponse


data class X(
        val clouds: Int = 0,
        val deg: Double = 0.0,
        val dt: Int = 0,
        val humidity: Int = 0,
        val pressure: Double = 0.0,
        val rain: Double = 0.0,
        val snow: Double = 0.0,
        val speed: Double = 0.0,
        val temp: Temp = Temp(),
        val weather: List<Weather> = listOf()
)