package com.weather.android.logic.network

import com.weather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}