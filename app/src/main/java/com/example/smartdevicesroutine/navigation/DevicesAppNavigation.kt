package com.example.smartdevicesroutine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartdevicesroutine.screen.HomeScreen

@Composable
fun DevicesAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DevicesAppScreens.HomeScreen.name
    ) {
        composable(DevicesAppScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
    }
}