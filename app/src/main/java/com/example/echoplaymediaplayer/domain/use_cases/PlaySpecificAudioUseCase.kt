package com.example.echoplaymediaplayer.domain.use_cases

import com.example.echoplaymediaplayer.domain.audio_service_helper.AudioServiceHelper
import javax.inject.Inject

class PlaySpecificAudioUseCase @Inject constructor(
    private val audioServiceHelper: AudioServiceHelper
) {
    operator fun invoke(index : Int) = audioServiceHelper.playSpecificAudio(index)
}