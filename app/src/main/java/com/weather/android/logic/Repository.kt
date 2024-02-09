package com.weather.android.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.weather.android.logic.model.Place
import com.weather.android.logic.model.TimeData
import com.weather.android.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }

    fun searchWeather(query: String) = liveData(Dispatchers.IO) {
        Log.v("xd", query)
        val result = try {
            val weatherResponse = WeatherNetwork.searchWeather(query)
            if (weatherResponse != null) {
                Log.v("xd", weatherResponse.toString())
                Result.success(weatherResponse.timelines.daily)
            }
            else {
                Result.failure(RuntimeException("Response is null"))
            }
        } catch (e: Exception) {
            Log.v("xd", "failed" + e.toString())
            Result.failure<List<TimeData>>(e)
        }
        emit(result)
    }
}