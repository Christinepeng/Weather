package com.weather.android.logic.network

import com.weather.android.logic.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("v4/weather/forecast?location=san%20francisco&apikey=ImAUAK80vu09qEi4eUcFUilDWRORaaOY")
    fun searchWeather(@Query("query") query: String): Call<WeatherResponse>
}