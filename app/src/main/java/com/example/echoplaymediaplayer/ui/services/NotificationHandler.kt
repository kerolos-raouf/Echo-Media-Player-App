package com.example.echoplaymediaplayer.ui.services

import android.app.PendingIntent
import android.content.Context
import android.media.session.MediaSession
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerNotificationManager

interface NotificationHandler {
    fun createNotificationChannel(
        channelId: String,
        channelName : String
    )
   // fun startForegroundNotification(channelId: String)
    fun getPendingIntent(serviceContext: Context, actionState: AudioService.AudioActionState): PendingIntent
}