package com.weather.android.ui.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.weather.android.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityName = intent.getStringExtra("city_name")
        val weatherFragment =
            supportFragmentManager.findFragmentById(binding.weatherFragment.id) as WeatherFragment
        weatherFragment.queryWeather(cityName!!)
    }

}