package ru.devsoland.meteowashkotlin.data.OWCResponse

data class OWCurrentWeatherResponse(
        val base: String,
        val clouds: Clouds,
        val cod: Int,
        val coord: Coord,
        val dt: Int,
        val id: Int,
        val main: Main,
        val name: String,
        val sys: Sys,
        val visibility: Float,
        val weather: List<Weather>,
        val wind: Wind
)