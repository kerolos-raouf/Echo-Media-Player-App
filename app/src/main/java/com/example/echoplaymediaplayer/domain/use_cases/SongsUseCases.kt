package com.example.echoplaymediaplayer.domain.use_cases

data class SongsUseCases (
    val getAllAudio: GetAllAudioUseCase,
    val playSpecificAudio: PlaySpecificAudioUseCase
)