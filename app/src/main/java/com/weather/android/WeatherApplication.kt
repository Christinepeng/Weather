package com.weather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.weather.android.logic.model.Location

class WeatherApplication : Application() {
    companion object {
        const val TOKEN = "hrWCevltc3xfWzjklzMpoV8dsRKQv227"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}