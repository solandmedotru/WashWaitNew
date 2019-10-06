package ru.solandme.washwait.ui.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ru.solandme.washwait.R
import ru.solandme.washwait.data.db.entity.WeatherEntity
import java.util.*


class RWForecastAdapter(private val listener: (WeatherEntity) -> Unit) : RecyclerView.Adapter<RWForecastAdapter.WeatherHolder>() {
    private var weatherForecasts: MutableList<WeatherEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.rw_forecast_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(weatherForecasts[position], listener)
    }

    override fun getItemCount() = weatherForecasts.size

    fun addItems(weatherForecasts: List<WeatherEntity>) {
        val diffUtilCallback = MyDiffUtilCallback(this.weatherForecasts, weatherForecasts)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback, true)
        diffResult.dispatchUpdatesTo(this)
        this.weatherForecasts.clear()
        this.weatherForecasts.addAll(weatherForecasts)
        notifyDataSetChanged()
    }

    inner class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView: View?
            get() = itemView

        fun bind(weather: WeatherEntity, listener: (WeatherEntity) -> Unit) {
            val textTempMax = itemView.findViewById<TextView>(R.id.rw_text_temp_max)
            val textTempMin = itemView.findViewById<TextView>(R.id.rw_text_temp_min)
            val textDescribe = itemView.findViewById<TextView>(R.id.rw_text_describe)
            val textDay = itemView.findViewById<TextView>(R.id.rw_text_day)
            val weatherIcon = itemView.findViewById<ImageView>(R.id.rw_weather_icon)

            textTempMin.text = weather.tempMin.toString() + "\u2103"
            textTempMax.text = weather.tempMax.toString() + "\u2103"
            textDescribe.text = weather.description
            textDay.text = getFormattedDate(weather.lastUpdate)
            weatherIcon.setImageResource(weather.icon)

            itemView.setOnClickListener {
                listener.invoke(weather)
            }
        }
    }

    fun getFormattedDate(timestamp: Long): String {
        val instance = Calendar.getInstance()
        instance.timeInMillis = timestamp * 1000L
        return String.format("%ta, %te %tb", instance, instance, instance)
    }
}

class MyDiffUtilCallback(
        private val oldList: List<WeatherEntity>,
        private val newList: List<WeatherEntity>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
    }

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
}
