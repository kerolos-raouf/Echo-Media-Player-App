package com.example.echoplaymediaplayer.ui.navigation

import com.example.echoplaymediaplayer.domain.util.Constants

sealed class Screen(val route : String) {
    data object Splash : Screen(Constants.SPLASH_SCREEN)
    data object OnBoarding : Screen(Constants.ON_BOARDING_SCREEN)
    data object Home : Screen(Constants.HOME_SCREEN)
    data object MainNavHost : Screen(Constants.MAIN_NAV_HOST)
    data object MainScreensSetup : Screen(Constants.MAIN_SCREENS_SETUP)
}