package com.weatherapp.models

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng

data class City(
    val name: String,
    var location: LatLng? = null,
    var weather: String? = null,
    var img_url: String? = null,
    var bitmap: Bitmap? = null
)