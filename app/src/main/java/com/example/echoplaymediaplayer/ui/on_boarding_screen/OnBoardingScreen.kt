package com.example.echoplaymediaplayer.ui.on_boarding_screen

import android.graphics.pdf.PdfDocument.Page
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.echoplaymediaplayer.R
import com.example.echoplaymediaplayer.ui.navigation.Screen
import com.example.echoplaymediaplayer.ui.theme.DarkPurple
import com.example.echoplaymediaplayer.ui.theme.LightBlack
import com.example.echoplaymediaplayer.ui.theme.LightPurple
import com.example.echoplaymediaplayer.ui.theme.robotoFontFamily
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val state = onBoardingViewModel.state
    val pagerState = rememberPagerState {
        state.value.pages.size
    }
    val scope = rememberCoroutineScope()


    Box(
        modifier = modifier
            .fillMaxSize()
            ,
        contentAlignment = Alignment.BottomCenter
    ){
        HorizontalPager(
            modifier = Modifier.matchParentSize()
                ,
            state = pagerState,
        ) {pageNumber->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight(0.7f),
                    painter = painterResource(id = state.value.pagesImages[pageNumber]),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop)
            }


        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(LightBlack)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.value.pages[pagerState.currentPage],
                color = Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontFamily = robotoFontFamily
            )

            PagesIndicator(
                numberOfPages = state.value.pages.size,
                currentPage = pagerState.currentPage
            )


            NextButton(modifier = Modifier,
                text = if(pagerState.currentPage == state.value.pages.size - 1) "Get Started" else "Next",) {
                if(pagerState.currentPage < state.value.pages.size - 1){
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }else{
                    navController.navigate(Screen.MainScreensSetup.route){
                        popUpTo(Screen.OnBoarding.route){
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
    
}

@Composable
fun PagesIndicator(
    numberOfPages : Int,
    currentPage : Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(numberOfPages) { index ->
            val isSelected = currentPage == index
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .size(if (isSelected) 12.dp else 8.dp)
                    .background(if (isSelected) LightPurple else Color.White)
            )
        }
    }
}

@Composable
fun NextButton(
    modifier: Modifier,
    text : String,
    onClick : () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = LightPurple
        )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}