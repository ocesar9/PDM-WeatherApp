package com.weatherapp.models

import com.google.android.gms.maps.model.LatLng

data class City(
    val name: String,
    var weather: String,
    var location: LatLng? = null
)