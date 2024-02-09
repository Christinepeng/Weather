package com.weather.android.logic.model

data class WeatherResponse(val timelines: Timelines, val location: Location)
data class Timelines(val minutely: List<TimeData>, val hourly: List<TimeData>, val daily: List<TimeData>)
data class Location(val lat: Double, val lon: Double, val name: String, val type: String)
data class TimeData(val time: String, val values: Value)
data class Value(val cloudBase :Double,
//                 val cloudCeiling : ,
                 val cloudCover : Double,
                 val dewPoint : Double,
                 val freezingRainIntensity : Double,
                 val humidity : Double,
                 val precipitationProbability : Double,
                 val pressureSurfaceLevel : Double,
                 val rainIntensity : Double,
                 val sleetIntensity : Double,
                 val snowIntensity : Double,
                 val temperature : Double,
                 val temperatureApparent : Double,
                 val uvHealthConcern : Double,
                 val uvIndex : Double,
                 val visibility : Double,
                 val weatherCode : Double,
                 val windDirection : Double,
                 val windGust : Double,
                 val windSpeed : Double)