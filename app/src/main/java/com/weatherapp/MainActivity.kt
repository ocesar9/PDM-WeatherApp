package com.weatherapp

import BottomNavBar
import CityDialog
import MainNavHost
import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weatherapp.components.nav.BottomNavItem
import com.weatherapp.ui.theme.WeatherAppTheme
import android.Manifest
import android.content.Intent
import androidx.compose.runtime.DisposableEffect
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.weatherapp.repo.Repository

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!viewModel.loggedIn) {
            this.finish()
        } else {
            setContent {
                var showDialog by remember { mutableStateOf(false) }
                val navController = rememberNavController()
                val repo = remember { Repository(viewModel, context = this@MainActivity) }

                DisposableEffect(Unit) {
                    val listener = androidx.core.util.Consumer<Intent> { intent ->
                        val name = intent.getStringExtra("city")
                        val city = viewModel.cities.find { it.name == name }
                        viewModel.city = city
                        if (city != null) {
                            repo.loadWeather(city)
                            repo.loadForecast(city)
                        }
                    }
                    addOnNewIntentListener(listener)
                    onDispose { removeOnNewIntentListener(listener) }
                }

                val context = LocalContext.current
                val currentRoute = navController.currentBackStackEntryAsState()
                val showButton = currentRoute.value?.destination?.route != BottomNavItem.MapPage.route
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = {}
                )

                WeatherAppTheme {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Welcome ${viewModel.user.name}") },
                                actions = {
                                    IconButton(
                                        onClick = {
                                            Firebase.auth.signOut()
                                            finish()
                                        }
                                    ) {
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
                            if (showButton) {
                                FloatingActionButton(onClick = { showDialog = true }) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Add"
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            MainNavHost(
                                navController = navController,
                                viewModel = viewModel,
                                repo = repo,
                                context = context
                            )
                        }
                    }

                    if (showDialog) {
                        CityDialog(
                            onDismiss = { showDialog = false },
                            onConfirm = { cityName ->
                                if (cityName.isNotBlank()) {
                                    repo.addCity(name = cityName)
                                }
                                showDialog = false
                            }
                        )
                    }
                }
            }
        }
    }
}



