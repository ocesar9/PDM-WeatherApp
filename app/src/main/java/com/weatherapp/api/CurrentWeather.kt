package com.weatherapp.api

data class CurrentWeather(
    var APILocation: Location? = null,
    var current: Weather? = null
)