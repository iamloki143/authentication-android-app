package com.loki.theapp.presentation.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authapp.ui.theme.Purple50
import com.example.authapp.ui.theme.Purple600

@Composable
fun AppBottomNav(
    currentRoute: String,
    onHome: () -> Unit,
    onDashboard: () -> Unit,
    onProfile: () -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick  = onHome,
            icon = { Icon(Icons.Outlined.Home, "Home") },
            label    = { Text("Home", fontSize = 11.sp) },
            colors   = NavigationBarItemDefaults.colors(
                selectedIconColor      = Purple600,
                selectedTextColor      = Purple600,
                indicatorColor         = Purple50
            )
        )
        NavigationBarItem(
            selected = currentRoute == "dashboard",
            onClick  = onDashboard,
            icon = { Icon(Icons.Outlined.BarChart, "Stats") },
            label    = { Text("Stats", fontSize = 11.sp) },
            colors   = NavigationBarItemDefaults.colors(
                selectedIconColor = Purple600,
                selectedTextColor = Purple600,
                indicatorColor    = Purple50
            )
        )
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick  = onProfile,
            icon = { Icon(Icons.Outlined.Person, "Profile") },
            label    = { Text("Profile", fontSize = 11.sp) },
            colors   = NavigationBarItemDefaults.colors(
                selectedIconColor = Purple600,
                selectedTextColor = Purple600,
                indicatorColor    = Purple50
            )
        )
    }
}