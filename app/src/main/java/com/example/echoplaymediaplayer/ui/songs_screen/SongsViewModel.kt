package com.example.echoplaymediaplayer.ui.songs_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echoplaymediaplayer.domain.use_cases.SongsUseCases
import com.example.echoplaymediaplayer.domain.util.SongsOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class SongsViewModel @Inject constructor(
    private val songsUseCases: SongsUseCases
) :ViewModel(){
    private val _state = mutableStateOf(SongsScreenState())
    val state : State<SongsScreenState> = _state


    private var songsJob : Job ?= null

    fun onEvent(songsScreenEvent: SongsScreenEvent){
        when(songsScreenEvent){
            is SongsScreenEvent.GetAllSongs -> getAllSongs(songsScreenEvent.songsOrder)
            is SongsScreenEvent.PlaySpecificAudio -> songsUseCases.playSpecificAudio(songsScreenEvent.index)
        }
    }


    private fun getAllSongs(songsOrder: SongsOrder){
        songsJob?.cancel()
        songsJob = songsUseCases.getAllAudio(songsOrder).onEach{
            _state.value = _state.value.copy(
                audioListResult = it
            )
            Log.d("Kerolos", "getAllSongs: $it")
        }.launchIn(viewModelScope)
    }
}