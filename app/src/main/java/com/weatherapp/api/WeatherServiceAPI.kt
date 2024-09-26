package com.weatherapp.api

import com.weatherapp.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServiceAPI {
    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
        const val API_KEY = BuildConfig.WEATHER_API_KEY
    }

    @GET("search.json?key=$API_KEY&lang=pt_br")
    fun search(
        @Query("q") query: String
    ): Call<List<Location>?>

    @GET("current.json?key=$API_KEY&lang=pt")
    fun currentWeather(@Query("q") query: String): Call<CurrentWeather?>

    @GET("forecast.json?key=$API_KEY&days=10&lang=pt")
    fun forecast(@Query("q") name: String): Call<WeatherForecast?>

}