package com.example.echoplaymediaplayer.ui.main_screens_setup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainScreensViewModel @Inject constructor(

) : ViewModel(){

    private val _state = mutableStateOf(MainScreensState(
        listOf(
            "Songs",
            "Playlists",
            "Folders",
            "Albums",
            "Artists",
        )
    ))

    val state : State<MainScreensState> = _state

}