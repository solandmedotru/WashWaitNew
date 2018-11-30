package ru.solandme.washwait.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.solandme.washwait.data.db.entity.CURRENT_WEATHER_ID
import ru.solandme.washwait.data.db.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currentWeatherEntity: CurrentWeatherEntity)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeather(): LiveData<CurrentWeatherEntity>
}