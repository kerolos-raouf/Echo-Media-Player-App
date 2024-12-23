package com.example.echoplaymediaplayer.domain.audio_service_helper

import com.example.echoplaymediaplayer.domain.model.Audio

interface AudioServiceHelper {
    fun playSpecificAudio(index: Int)
    fun playNextAudio()
    fun playPreviousAudio()
    fun pauseAudio()
    fun resumeAudio()
}