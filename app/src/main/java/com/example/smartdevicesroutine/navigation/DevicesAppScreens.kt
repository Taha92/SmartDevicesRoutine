package com.example.smartdevicesroutine.navigation

enum class DevicesAppScreens {
    ShoppingSplashScreen,
    HomeScreen;

    companion object {
        fun fromRoute(route: String?): DevicesAppScreens
                = when(route?.substringBefore("/")) {
            ShoppingSplashScreen.name -> ShoppingSplashScreen
            HomeScreen.name -> HomeScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}