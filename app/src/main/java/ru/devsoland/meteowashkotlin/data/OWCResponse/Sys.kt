package ru.devsoland.meteowashkotlin.data.OWCResponse

data class Sys(
        val country: String,
        val message: Double,
        val sunrise: Int,
        val sunset: Int
)