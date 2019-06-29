package ru.solandme.washwait.data.net.yandex.yResponse

data class Tzinfo(
        val abbr: String,
        val dst: Boolean,
        val name: String,
        val offset: Int
)