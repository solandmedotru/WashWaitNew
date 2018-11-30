package ru.solandme.washwait.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
        var humidity: Int,
        var pressure: Double,
        var temp: Double,
        var tempMax: Double,
        var tempMin: Double,
        @Embedded(prefix = "wind_")
        var wind: Wind,
        var description: String,
        var icon: String,
        var locationLat: Double,
        var locationLon: Double,
        var locationName: String,
        var lastUpdate: Long
)
{
        @PrimaryKey(autoGenerate = false)
        var id: Int = CURRENT_WEATHER_ID
}