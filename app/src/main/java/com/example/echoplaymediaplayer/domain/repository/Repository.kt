package com.example.echoplaymediaplayer.domain.repository

import com.example.echoplaymediaplayer.domain.model.Audio
import com.example.echoplaymediaplayer.domain.util.Result
import com.example.echoplaymediaplayer.domain.util.SongsOrder
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAllAudio(
        songsOrder: SongsOrder
    ): Flow<Result<List<Audio>>>

    fun getAudioAtIndex(index : Int) : Audio

    fun getNextIndex() : Int

    fun getPreviousIndex() : Int

    fun getCurrentIndex() : Int
}