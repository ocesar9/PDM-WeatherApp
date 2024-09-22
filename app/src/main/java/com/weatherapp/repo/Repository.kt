package com.weatherapp.repo

import com.google.android.gms.maps.model.LatLng
import com.weatherapp.api.WeatherService
import com.weatherapp.db.FBDatabase
import com.weatherapp.models.City
import com.weatherapp.models.Forecast
import com.weatherapp.models.User
import com.weatherapp.models.Weather

class Repository(private var listener: Listener) : FBDatabase.Listener {
    private var fbDb = FBDatabase(this)
    private var weatherService = WeatherService()

    interface Listener {
        fun onUserLoaded(user: User)
        fun onCityAdded(city: City)
        fun onCityRemoved(city: City)
        fun onCityUpdated(city: City)
    }

    fun addCity(name: String) {
        weatherService.getLocation(name) { lat, lng ->
            if (lat != null && lng != null) {
                fbDb.add(
                    City(
                        name = name,
                        location = LatLng(lat, lng)
                    )
                )
            } else {
                println("Location not found for the city: $name")
            }
        }
    }

    fun addCity(lat: Double, lng: Double) {
        weatherService.getName(lat, lng) { name ->
            fbDb.add(
                City(
                    name = name ?: "NOT_FOUND",
                    location = LatLng(lat, lng),
                )
            )
        }
    }

    fun loadWeather(city: City) {
        weatherService.getCurrentWeather(city.name) { apiWeather ->
            city.weather =
                Weather(
                    date = apiWeather?.current?.last_updated ?: "...",
                    desc = apiWeather?.current?.condition?.text ?: "...",
                    temp = apiWeather?.current?.temp_c ?: -1.0,
                    imgUrl = "https:" + apiWeather?.current?.condition?.icon
                )
            listener.onCityUpdated(city)
        }
    }

    fun remove(city: City) {
        fbDb.remove(city)
    }

    fun register(userName: String, email: String) {
        fbDb.register(User(userName, email))
    }

    fun loadForecast(city: City) {
        weatherService.getForecast(city.name) { result ->
            city.forecast = result?.forecast?.forecastday?.map {
                Forecast(
                    date = it.date ?: "00-00-0000",
                    weather = it.day?.condition?.text ?: "Erro carregando!",
                    tempMin = it.day?.mintemp_c ?: -1.0,
                    tempMax = it.day?.maxtemp_c ?: -1.0,
                    imgUrl = ("https:" + it.day?.condition?.icon)
                )
            }
            listener.onCityUpdated (city)
        }
    }

    override fun onUserLoaded(user: User) {
        listener.onUserLoaded(user)
    }

    override fun onCityAdded(city: City) {
        listener.onCityAdded(city)
    }

    override fun onCityRemoved(city: City) {
        listener.onCityRemoved(city)
    }
}