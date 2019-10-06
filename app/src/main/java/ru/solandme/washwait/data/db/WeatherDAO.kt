package ru.solandme.washwait.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.solandme.washwait.data.db.entity.CURRENT_WEATHER_ID
import ru.solandme.washwait.data.db.entity.WeatherEntity

@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntity: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weatherEntity: List<WeatherEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(weatherEntity: WeatherEntity)

    @Query("select * from weather where id = $CURRENT_WEATHER_ID")
    fun getCurrentWeather(): LiveData<WeatherEntity>

    @Query("select * from weather where id = :id")
    fun getWeatherById(id: Int): LiveData<WeatherEntity>

    @Query("SELECT * FROM weather ORDER BY id")
    fun getWeathers(): LiveData<List<WeatherEntity>>

    @Query("SELECT lastUpdate FROM weather where id = $CURRENT_WEATHER_ID")
    fun getLastUpdate(): LiveData<Long>
}