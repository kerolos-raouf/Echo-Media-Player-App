package com.example.echoplaymediaplayer.ui.on_boarding_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.echoplaymediaplayer.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor (

) : ViewModel(){
    private val _state = mutableStateOf(OnBoardingScreenState(
        pages = listOf(
            "User friendly music player for your device",
            "We provide you with the best experience possible",
            "Listen to the best audio & music with Echo Play now!"
        ),
        pagesImages = listOf(
            R.drawable.page1,
            R.drawable.page2,
            R.drawable.page3
        )
    ))
    val state : State<OnBoardingScreenState> = _state
}