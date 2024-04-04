package com.weatherapp.components.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String){
    data object HomePage : BottomNavItem("Home", Icons.Default.Home, "home")
    data object ListPage : BottomNavItem("List", Icons.Default.Favorite, "list")
    data object MapPage : BottomNavItem("Map", Icons.Default.LocationOn, "map")
}