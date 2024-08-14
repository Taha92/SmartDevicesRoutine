package com.example.smartdevicesroutine.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartdevicesroutine.screen.HomeScreen
import com.example.smartdevicesroutine.screen.MainViewModel

@Composable
fun DevicesAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DevicesAppScreens.HomeScreen.name
    ) {
        composable(DevicesAppScreens.HomeScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            HomeScreen(navController = navController, mainViewModel)
        }
    }
}