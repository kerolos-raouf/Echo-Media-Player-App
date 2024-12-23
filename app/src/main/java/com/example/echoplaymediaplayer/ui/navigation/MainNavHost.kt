package com.example.echoplaymediaplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.echoplaymediaplayer.ui.home_screen.HomeScreen

@Preview(showSystemUi = true)
@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        route = Screen.MainNavHost.route
    ){
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen()
        }
    }
}