package io.devexpert.stateandinteroperability

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.fragment.compose.AndroidFragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.devexpert.stateandinteroperability.ui.screens.detail.DetailScreen
import io.devexpert.stateandinteroperability.ui.screens.home.HomeFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController , startDestination = "home") {
                composable("home") {
                    AndroidFragment<HomeFragment>(modifier = Modifier.fillMaxSize())
                }
                composable("detail") {
                    DetailScreen()
                }
            }
        }
    }
}