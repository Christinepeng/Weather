package com.weather.android.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.weather.android.R
import com.weather.android.logic.model.DailyTimeData
import com.weather.android.logic.model.HourlyTimeData

class WeatherAdapter(private val fragment: Fragment, private val weatherList: List<HourlyTimeData>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time: TextView = view.findViewById(R.id.time)
        val temperature: TextView = view.findViewById(R.id.temperature)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.weather_item,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.time.text = "time: ${weather.time}"
        holder.temperature.text = "temperature: ${ weather.values.temperature.toString() }"
    }

    override fun getItemCount() = weatherList.size
}