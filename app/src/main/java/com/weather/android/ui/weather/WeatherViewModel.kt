package com.weather.android.ui.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.weather.android.logic.Repository
import com.weather.android.logic.model.TimeData

class WeatherViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val weatherList = ArrayList<TimeData>()
    val weatherLiveData = searchLiveData.switchMap { query ->
        Repository.searchWeather(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}