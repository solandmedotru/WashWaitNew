package ru.solandme.washwait.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "weather")
data class WeatherEntity(
        @PrimaryKey(autoGenerate = false)
        var id: Int = 0,
        var humidity: Int = 0,
        var pressure: Double = 0.0,
        var temp: Double = 0.0,
        var tempMax: Double = 0.0,
        var tempMin: Double = 0.0,
        @Embedded(prefix = "wind_")
        var wind: Wind = Wind(),
        var description: String = "",
        var icon: String = "",
        @Embedded(prefix = "location_")
        var location: Location = Location(),
        var lastUpdate: Long = 0L
)
