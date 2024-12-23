package com.example.echoplaymediaplayer.ui.songs_screen

import com.example.echoplaymediaplayer.domain.model.Audio
import com.example.echoplaymediaplayer.domain.util.Result

data class SongsScreenState (
    val audioListResult: Result<List<Audio>> = Result.Loading
)