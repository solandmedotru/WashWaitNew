package ru.solandme.washwait.data.net.OWCResponse

data class Sys(
        val country: String,
        val message: Double,
        val sunrise: Int,
        val sunset: Int
)