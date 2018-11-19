package ru.devsoland.meteowashkotlin.data.OWCResponse

data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
)