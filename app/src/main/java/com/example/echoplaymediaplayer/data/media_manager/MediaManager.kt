package com.example.echoplaymediaplayer.data.media_manager

import com.example.echoplaymediaplayer.domain.model.Audio
import com.example.echoplaymediaplayer.domain.util.Result
import com.example.echoplaymediaplayer.domain.util.SongsOrder
import kotlinx.coroutines.flow.Flow

interface MediaManager {
    fun getAllAudio(songsOrder: SongsOrder): List<Audio>
}