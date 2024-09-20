package com.weatherapp.api

data class Location(
    var id: String? = null,
    var name: String? = null,
    var region: String? = null,
    var country: String? = null,
    var lat: Double? = null,
    var lon: Double? = null,
    var url: String? = null
)