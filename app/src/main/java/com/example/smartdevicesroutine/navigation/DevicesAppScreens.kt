package com.example.smartdevicesroutine.navigation

enum class DevicesAppScreens {
    SplashScreen,
    HomeScreen,
    AddRoutineScreen;

    companion object {
        fun fromRoute(route: String?): DevicesAppScreens
                = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            HomeScreen.name -> HomeScreen
            AddRoutineScreen.name -> AddRoutineScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}