package com.loki.theapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.authapp.ui.theme.AuthAppTheme
import com.loki.theapp.navigation.NavGraph
import com.loki.theapp.presentation.screen.auth.AuthScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavGraph()
        }
    }
}