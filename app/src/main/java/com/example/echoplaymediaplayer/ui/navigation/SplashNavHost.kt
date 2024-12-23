package com.example.echoplaymediaplayer.ui.navigation

import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.echoplaymediaplayer.data.permissions.PermissionManager
import com.example.echoplaymediaplayer.domain.util.Constants
import com.example.echoplaymediaplayer.ui.main_screens_setup.MainScreensSetup
import com.example.echoplaymediaplayer.ui.on_boarding_screen.OnBoardingScreen
import com.example.echoplaymediaplayer.ui.spalsh_screen.SplashScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavHostSetup(
    permissionManager: PermissionManager
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Constants.SPLASH_SCREEN
    ){
        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.OnBoarding.route
        ) {
            OnBoardingScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.MainScreensSetup.route
        ) {
            MainScreensSetup(permissionManager = permissionManager)
        }


    }
}