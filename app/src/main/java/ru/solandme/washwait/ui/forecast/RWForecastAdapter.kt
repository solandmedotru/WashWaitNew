package ru.solandme.washwait.ui.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.entity.WeatherEntity
import java.util.*


class RWForecastAdapter : RecyclerView.Adapter<RWForecastAdapter.WeatherHolder>() {
    var weatherForecasts: List<WeatherEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.rw_forecast_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(weatherForecasts[position])
    }

    override fun getItemCount() = weatherForecasts.size

    fun addItems(weatherForecasts: List<WeatherEntity>) {
        this.weatherForecasts = weatherForecasts
        notifyDataSetChanged()
    }

    inner class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: WeatherEntity) {
            val textTempMax = itemView.findViewById(R.id.rw_text_temp_max) as TextView
            val textTempMin = itemView.findViewById(R.id.rw_text_temp_min) as TextView
            val textDescribe = itemView.findViewById(R.id.rw_text_describe) as TextView
            val textDay = itemView.findViewById(R.id.rw_text_day) as TextView
            val weatherIcon = itemView.findViewById(R.id.rw_weather_icon) as ImageView

            textTempMin.text = weather.tempMin.toString() + "\u2103"
            textTempMax.text = weather.tempMax.toString() + "\u2103"
            textDescribe.text = weather.description
            textDay.text = getFormattedDate(weather.lastUpdate)
            weatherIcon.setImageResource(weather.icon)
        }
    }

    fun getFormattedDate(timestamp: Long): String {
        val instance = Calendar.getInstance()
        instance.timeInMillis = timestamp*1000L
        return String.format("%ta, %te %tb", instance, instance, instance)
    }
}