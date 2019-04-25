package ru.solandme.washwait.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.solandme.washwait.data.db.entity.WeatherEntity


@Database(
        entities = [WeatherEntity::class],
        version = 1
)
abstract class ForecastDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO

    companion object {
        @Volatile
        private var instance: ForecastDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        ForecastDataBase::class.java, "forecast.db")
                        .build()
    }
}