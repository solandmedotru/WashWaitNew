package ru.solandme.washwait.data.net.OWCResponse

data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
)