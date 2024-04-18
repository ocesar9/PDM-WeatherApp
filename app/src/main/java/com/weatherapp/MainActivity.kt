package com.weatherapp

import BottomNavBar
import CityDialog
import MainNavHost
import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showDialog by remember { mutableStateOf(false) }
            val viewModel : MainViewModel by viewModels()
            val navController = rememberNavController()
            WeatherAppTheme {
                if (showDialog)(
                    CityDialog(
                        onDismiss = { showDialog = false },
                        onConfirm = { city ->
                            if (city.isNotBlank()) viewModel.add(city)
                            showDialog = false
                        }
                    )
                )
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Welcome!")
                            },
                            actions = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = { BottomNavBar(navController = navController) },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {showDialog = true }
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add"
                            )
                        }
                    }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainNavHost(
                            navController = navController,
                            viewModel
                        )
                    }
                }
            }
        }
    }
}

