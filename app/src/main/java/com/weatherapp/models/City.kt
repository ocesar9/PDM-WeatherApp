package com.weatherapp.models

import com.google.android.gms.maps.model.LatLng

data class City(
    val name: String,
    var location: LatLng? = null,
    var weather: Weather? = null,
    var forecast: List<Forecast>? = null,
)