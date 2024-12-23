package com.example.echoplaymediaplayer.domain.audio_service_helper

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.echoplaymediaplayer.domain.repository.Repository
import com.example.echoplaymediaplayer.ui.services.AudioService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AudioServiceHelperImpl @Inject constructor(
    @ApplicationContext private val context : Context,
    private val repository: Repository
) : AudioServiceHelper {
    override fun playSpecificAudio(index: Int) {
        playAudio(AudioService.AudioActionState.ACTION_PLAY.name, index)
    }

    override fun playNextAudio() {
        val index = repository.getNextIndex()
        playAudio(AudioService.AudioActionState.ACTION_PLAY.name, index)
    }

    override fun playPreviousAudio() {
        val index = repository.getPreviousIndex()
        playAudio(AudioService.AudioActionState.ACTION_PLAY.name, index)
    }

    override fun pauseAudio() {
        val index = repository.getCurrentIndex()
        playAudio(AudioService.AudioActionState.ACTION_PAUSE.name, index)
    }

    override fun resumeAudio() {
        val index = repository.getCurrentIndex()
        playAudio(AudioService.AudioActionState.ACTION_PLAY.name, index)
    }

    private fun playAudio(action : String, songIndex : Int){
        val intent =  Intent(context, AudioService::class.java).apply {
            this.action = action
            this.putExtra(AudioService.SONG_INDEX, songIndex)
        }
        context.startService(intent)
    }
}