package com.weather.android.logic.network

import com.weather.android.logic.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("v4/weather/forecast")
    fun searchWeather(@Query("location") query: String, @Query("apikey") apikey: String = "ImAUAK80vu09qEi4eUcFUilDWRORaaOY"): Call<WeatherResponse>
}