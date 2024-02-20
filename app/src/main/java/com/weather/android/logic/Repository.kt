package com.weather.android.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.weather.android.logic.dao.PlaceDao
import com.weather.android.logic.model.DailyTimeData
import com.weather.android.logic.model.HourlyTimeData
import com.weather.android.logic.model.Place
import com.weather.android.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        Log.v("xd", "start searchPlaces(), query: $query")
        val placeResponse = WeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun searchWeather(query: String) = fire(Dispatchers.IO) {
        Log.v("xd", "query + $query + |")
        val weatherResponse = WeatherNetwork.searchWeather(query)
        Log.v("xd", weatherResponse.toString().length.toString())
        Result.success(weatherResponse.timelines.hourly)
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Log.v("xd", "failed" + e.toString())
                Result.failure<T>(e)
            }
            emit(result)
        }
    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}