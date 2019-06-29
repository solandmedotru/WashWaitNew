package ru.solandme.washwait.data.net.owm.owcResponse

data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
)