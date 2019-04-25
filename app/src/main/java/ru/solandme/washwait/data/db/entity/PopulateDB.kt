package ru.solandme.washwait.data.db.entity

class initializeDBonFirstRun {
    fun go(): WeatherEntity {
        return WeatherEntity(
                0,
                0.0,
                0.0,
                0.0,
                0.0,
                Wind(0.0),
                "",
                "",
                Location(0.0, 0.0),
                0 )
    }
}