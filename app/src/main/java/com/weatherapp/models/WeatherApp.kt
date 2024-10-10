package com.weatherapp.models

import android.app.Application
import com.weatherapp.monitor.ForecastMonitor
import com.weatherapp.repo.Repository

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val monitor = ForecastMonitor(this)
        val repo = Repository(monitor, context = this)
    }
}