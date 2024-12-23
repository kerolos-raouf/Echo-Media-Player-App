package com.example.echoplaymediaplayer.di

import com.example.echoplaymediaplayer.domain.audio_service_helper.AudioServiceHelper
import com.example.echoplaymediaplayer.domain.repository.Repository
import com.example.echoplaymediaplayer.domain.use_cases.GetAllAudioUseCase
import com.example.echoplaymediaplayer.domain.use_cases.PlaySpecificAudioUseCase
import com.example.echoplaymediaplayer.domain.use_cases.SongsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {

    @Provides
    @Singleton
    fun provideSongsUseCases(repository: Repository,audioServiceHelper: AudioServiceHelper): SongsUseCases {
        return SongsUseCases(
            getAllAudio = GetAllAudioUseCase(repository = repository),
            playSpecificAudio = PlaySpecificAudioUseCase(audioServiceHelper = audioServiceHelper)
        )
    }

}