package ru.solandme.washwait.ui.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.entity.WeatherEntity

class RWForecastAdapter: RecyclerView.Adapter<RWForecastAdapter.WeatherHolder>() {
    var weatherForecasts: List<WeatherEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.rw_forecast_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bindViewItems(weatherForecasts[position])
    }

    override fun getItemCount() = weatherForecasts.size

    fun addItems(weatherForecasts: List<WeatherEntity>){
        this.weatherForecasts = weatherForecasts
        notifyDataSetChanged()
    }

    inner class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViewItems(weather: WeatherEntity){
            val textTempMax = itemView.findViewById(R.id.textTempMax) as TextView
            val textTempMin = itemView.findViewById(R.id.textTempMin) as TextView

            textTempMin.text = weather.tempMin.toString()+"\u2103"
            textTempMax.text = weather.tempMax.toString()+"\u2103"
        }
    }
}