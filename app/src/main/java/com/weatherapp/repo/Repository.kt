package com.weatherapp.repo

import com.google.android.gms.maps.model.LatLng
import com.weatherapp.api.WeatherService
import com.weatherapp.db.FBDatabase
import com.weatherapp.models.City
import com.weatherapp.models.User

class Repository(private var listener: Listener) : FBDatabase.Listener {
    private var fbDb = FBDatabase(this)
    private var weatherService = WeatherService()

    interface Listener {
        fun onUserLoaded(user: User)
        fun onCityAdded(city: City)
        fun onCityRemoved(city: City)
    }

    fun addCity(name: String) {
        weatherService.getLocation(name) { lat, lng ->
            if (lat != null && lng != null) {
                fbDb.add(
                    City(
                        name = name,
                        weather = "loading...",
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
                    weather = "loading..."
                )
            )
        }
    }

    fun remove(city: City) {
        fbDb.remove(city)
    }

    fun register(userName: String, email: String) {
        fbDb.register(User(userName, email))
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