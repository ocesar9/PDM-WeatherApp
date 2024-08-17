package com.weatherapp


import LoginPage
import MainViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.weatherapp.ui.theme.WeatherAppTheme
import com.weatherapp.utils.IntentUtils

class LoginActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.loggedIn) {
            val intent = IntentUtils.createMainActivityIntent(this)
            startActivity(intent)
            finish()
        }else{
            setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginPage()
                }
            }
        }
        }
    }
}

