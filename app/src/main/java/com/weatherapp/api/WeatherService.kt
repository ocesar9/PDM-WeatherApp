package com.weatherapp.api

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherService {
    private var weatherAPI: WeatherServiceAPI

    init {
        val retrofitAPI = Retrofit.Builder().baseUrl(WeatherServiceAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherAPI = retrofitAPI.create(WeatherServiceAPI::class.java)
    }

    fun getName(lat: Double, lng: Double, onResponse: (String?) -> Unit) {
        search("$lat,$lng") { loc -> onResponse(loc?.name) }
    }

    fun getLocation(name: String, onResponse: (lat: Double?, long: Double?) -> Unit) {
        search(name) { loc -> onResponse(loc?.lat, loc?.lon) }
    }


    private fun search(query: String, onResponse: (Location?) -> Unit) {
        val call: Call<List<Location>?> = weatherAPI.search(query)
        call.enqueue(object : Callback<List<Location>?> {
            override fun onResponse(
                call: Call<List<Location>?>,
                response: Response<List<Location>?>
            ) {
                onResponse(response.body()?.get(0))
            }

            override fun onFailure(call: Call<List<Location>?>, t: Throwable) {
                Log.w("WeatherApp WARNING", "" + t.message)
                onResponse(null)
            }
        })
    }

    private fun <T> enqueue(call: Call<T?>, onResponse: ((T?) -> Unit)? = null) {
        call.enqueue(object : Callback<T?> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                val obj: T? = response.body()
                onResponse?.invoke(obj)
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                Log.w("WeatherApp WARNING", "" + t.message)
            }
        })
    }

    fun getCurrentWeather(name: String, onResponse: (CurrentWeather?) -> Unit) {
        val call: Call<CurrentWeather?> = weatherAPI.currentWeather(name)
        enqueue(call) {
            onResponse.invoke(it)
        }
    }

    fun getForecast(name: String, onResponse: (WeatherForecast?) -> Unit) {
        val call: Call<WeatherForecast?> =
            weatherAPI.forecast(name)
        enqueue(call) {
            onResponse.invoke(it)
        }
    }

    fun getBitmap(imgUrl: String, onResponse: (Bitmap?) -> Unit) {
        Picasso.get().load(imgUrl).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                onResponse.invoke(bitmap)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.w("WeatherApp WARNING", "" + e?.message)
                e ?. printStackTrace ()
            }
        })
    }

}