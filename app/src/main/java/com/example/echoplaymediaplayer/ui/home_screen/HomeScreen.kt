package com.example.echoplaymediaplayer.ui.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview(showSystemUi = true)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    Scaffold (
        containerColor = Color.Transparent
    ){
        Box (
            modifier = Modifier.fillMaxSize().padding(it)
        ){
            Text(text = "Home Screen", fontSize = 30.sp)
        }
    }

}