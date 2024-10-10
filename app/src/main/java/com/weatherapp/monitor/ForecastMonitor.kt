package com.weatherapp.monitor

import android.app.NotificationManager
import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.weatherapp.models.City
import com.weatherapp.models.User
import com.weatherapp.repo.Repository
import java.util.concurrent.TimeUnit

class ForecastMonitor(context: Context) : Repository.Listener {
    private val wm = WorkManager.getInstance(context)
    private val nm = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    private fun updateMonitor(city: City) {
        cancelCity(city)
        if (!city.isMonitored) return;
        val inputData = Data.Builder().putString("city", city.name).build()
        val request = PeriodicWorkRequestBuilder<ForecastWorker>(
            repeatInterval = 15, repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).setInitialDelay(
            duration = 10, timeUnit = TimeUnit.SECONDS
        ).setInputData(inputData).build()
        wm.enqueueUniquePeriodicWork(
            city.name,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, request
        )
    }

    private fun cancelCity(city: City) {
        wm.cancelUniqueWork(city.name)
        nm.cancel(city.name.hashCode())
    }

    private fun cancelAll() {
        wm.cancelAllWork()
        nm.cancelAll()
    }

    override fun onUserLoaded(user: User) { /* DO NOTHING */
    }

    override fun onUserSignOut() = cancelAll()
    override fun onCityAdded(city: City) = updateMonitor(city)
    override fun onCityRemoved(city: City) = cancelCity(city)
    override fun onCityUpdated(city: City) = updateMonitor(city)
}