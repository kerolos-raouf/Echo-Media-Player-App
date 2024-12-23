package com.example.echoplaymediaplayer.ui.songs_screen

import com.example.echoplaymediaplayer.domain.util.SongsOrder

sealed class SongsScreenEvent {
    data class GetAllSongs(val songsOrder: SongsOrder) : SongsScreenEvent()
    data class PlaySpecificAudio(val index : Int) : SongsScreenEvent()
}