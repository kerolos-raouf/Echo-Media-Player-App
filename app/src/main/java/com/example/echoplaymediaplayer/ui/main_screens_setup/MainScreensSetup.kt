package com.example.echoplaymediaplayer.ui.main_screens_setup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.echoplaymediaplayer.R
import com.example.echoplaymediaplayer.data.permissions.PermissionManager
import com.example.echoplaymediaplayer.ui.home_screen.HomeScreen
import com.example.echoplaymediaplayer.ui.songs_screen.SongsScreen
import com.example.echoplaymediaplayer.ui.theme.DarkPurple
import com.example.echoplaymediaplayer.ui.theme.LightPurple
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainScreensSetup(
    permissionManager: PermissionManager,
    navHostController: NavHostController = rememberNavController(),
    mainScreensViewModel: MainScreensViewModel = hiltViewModel()
) {
    val state = mainScreensViewModel.state

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState {
        state.value.pages.size
    }


    ModalNavigationDrawer(
        drawerContent = { 
            ModalDrawerSheet{
                DrawerContent()
            }
        },
        drawerState = drawerState
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(DarkPurple.toArgb()),
                        Color(LightPurple.toArgb())
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )){
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    CustomTopAppBar {
                        drawerState.apply {
                            scope.launch {
                                if (isOpen) close() else open()
                            }
                        }
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .background(Color.Transparent)
                ) {
                    CustomTabRow(pagerState = pagerState, pages = state.value.pages)

                    HorizontalPager(
                        modifier = Modifier.background(Color.Transparent),
                        state = pagerState
                    ) {pageNumber->
                        when(pageNumber){
                            0->{
                                SongsScreen(permissionManager = permissionManager)
                            }
                            else->{
                                HomeScreen()
                            }
                        }

                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    onIconClicked : ()->Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {  },
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .clickable {
                        onIconClicked()
                    }
                    .padding(10.dp)
                    .size(30.dp),
                painter = painterResource(id = R.drawable.icon_menu),
                contentDescription = "")
        },
        actions = {
            Icon(
                modifier = Modifier
                    .clickable {

                    }
                    .padding(10.dp)
                    .size(30.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon")
        }
    )
}

@Composable
fun CustomTabRow(
    pagerState : PagerState,
    pages : List<String>,
) {
    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        modifier = Modifier.padding(bottom = 5.dp),
        selectedTabIndex = pagerState.currentPage ,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .padding(horizontal = 30.dp)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                color = Color.White,
                height = 2.dp
            )
        },
        divider = {},
        edgePadding = 0.dp,
        containerColor = Color.Transparent
    ) {
        pages.forEachIndexed{ index, page ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = page,
                        fontSize = if(pagerState.currentPage == index) 16.sp else 11.sp,
                        color = if(pagerState.currentPage == index) Color.White else Color.LightGray,
                        fontWeight = if(pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier
) {

}

