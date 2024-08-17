package com.weatherapp.utils

import android.content.Context
import android.content.Intent
import com.weatherapp.MainActivity

object IntentUtils {
    fun createMainActivityIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }
}