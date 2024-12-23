package com.example.echoplaymediaplayer.data.repository

import com.example.echoplaymediaplayer.data.media_manager.MediaManager
import com.example.echoplaymediaplayer.domain.model.Audio
import com.example.echoplaymediaplayer.domain.repository.Repository
import com.example.echoplaymediaplayer.domain.util.Result
import com.example.echoplaymediaplayer.domain.util.SongsOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val mediaManager: MediaManager
) : Repository {
    private var currentAudioList : List<Audio> = emptyList()
    private var currentIndex : Int = 0

    override fun getAllAudio(
        songsOrder: SongsOrder
    ): Flow<Result<List<Audio>>> = flow {
        emit(Result.Loading)
        try {
            currentAudioList = mediaManager.getAllAudio(songsOrder)
            emit(Result.Success(currentAudioList))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    override fun getAudioAtIndex(index: Int): Audio {
        currentIndex = index % currentAudioList.size
        return currentAudioList[currentIndex]
    }

    override fun getNextIndex(): Int {
        currentIndex = (currentIndex + 1) % currentAudioList.size
        return currentIndex
    }

    override fun getPreviousIndex(): Int {
        currentIndex = (currentIndex - 1 + currentAudioList.size) % currentAudioList.size
        return currentIndex
    }

    override fun getCurrentIndex(): Int {
        return currentIndex
    }


}