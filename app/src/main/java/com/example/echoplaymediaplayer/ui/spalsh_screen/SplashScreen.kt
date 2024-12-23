package com.example.echoplaymediaplayer.ui.spalsh_screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.echoplaymediaplayer.R
import com.example.echoplaymediaplayer.domain.util.Constants
import com.example.echoplaymediaplayer.ui.navigation.Screen
import com.example.echoplaymediaplayer.ui.theme.DarkPurple
import com.example.echoplaymediaplayer.ui.theme.LightBlack
import com.example.echoplaymediaplayer.ui.theme.LightPurple

//@Preview(showSystemUi = true)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val alpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000
            )
        )
        navController.navigate(Screen.OnBoarding.route){
            popUpTo(Screen.Splash.route){
                inclusive = true
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        DarkPurple,
                        LightPurple
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ){

        Column(
            modifier = Modifier.matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .alpha(alpha.value),
                painter = painterResource(id = R.drawable.splash2),
                contentDescription = "Logo")
        }

    }
}