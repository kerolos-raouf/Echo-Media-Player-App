package com.example.echoplaymediaplayer.di

import com.example.echoplaymediaplayer.data.media_manager.MediaManager
import com.example.echoplaymediaplayer.data.media_manager.MediaManagerImpl
import com.example.echoplaymediaplayer.data.permissions.PermissionManager
import com.example.echoplaymediaplayer.data.permissions.PermissionManagerImpl
import com.example.echoplaymediaplayer.domain.repository.Repository
import com.example.echoplaymediaplayer.data.repository.RepositoryImpl
import com.example.echoplaymediaplayer.domain.audio_service_helper.AudioServiceHelper
import com.example.echoplaymediaplayer.domain.audio_service_helper.AudioServiceHelperImpl
import com.example.echoplaymediaplayer.ui.services.NotificationHandler
import com.example.echoplaymediaplayer.ui.services.NotificationHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindPermissionManager(permissionManager: PermissionManagerImpl): PermissionManager


    @Binds
    @Singleton
    abstract fun bindMediaManager(mediaManager: MediaManagerImpl): MediaManager

    @Binds
    @Singleton
    abstract fun bindRepository(repository: RepositoryImpl): Repository

    @Binds
    @Singleton
    abstract fun bindAudioServiceHelper(audioServiceHelper: AudioServiceHelperImpl): AudioServiceHelper

    @Binds
    @Singleton
    abstract fun bindNotificationHandler(notificationHandler: NotificationHandlerImpl) : NotificationHandler
}