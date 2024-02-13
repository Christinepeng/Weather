package com.weather.android.ui.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.weather.android.logic.Repository
import com.weather.android.logic.model.DailyTimeData
import com.weather.android.logic.model.HourlyTimeData
import com.weather.android.logic.model.Location

class WeatherViewModel : ViewModel() {

//    private val locationLiveData = MutableLiveData<Location>()
//    var locationLng = ""
//    var locationLat = ""
    var placeName = ""
//    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
//        Repository.refreshWeather(location.lng, location.lat)
//    }
//    fun refreshWeather(lng: String, lat: String) {
//        locationLiveData.value = Location(lng, lat)
//    }

    private val searchLiveData = MutableLiveData<String>()
    val weatherList = ArrayList<HourlyTimeData>()
    val weatherLiveData = searchLiveData.switchMap { query ->
        Repository.searchWeather(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
        placeName = query
    }
}