package com.weatherapp.api



data class WeatherForecast(
    var location: Location? = null,
    var current: WeatherForecast? = null,
    var forecast: Forecast? = null,

)

data class Forecast(
    var forecastday: List<ForecastDay>? = null
)

data class ForecastDay(
    var date: String? = null,
    var day: Weather? = null
)