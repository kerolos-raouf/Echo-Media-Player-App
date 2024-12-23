package com.example.echoplaymediaplayer.ui.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerNotificationManager
import com.example.echoplaymediaplayer.ui.services.AudioService.AudioActionState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHandlerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationHandler {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(
        channelId: String,
        channelName : String
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun getPendingIntent(serviceContext: Context,actionState: AudioActionState): PendingIntent {
        return when(actionState)
        {
            AudioActionState.ACTION_PLAY -> {
                ///play intent
                val playIntent = Intent(serviceContext, AudioService::class.java).apply {
                    action = AudioActionState.ACTION_PLAY.name
                }
                val playPendingIntent = PendingIntent.getService(serviceContext, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)
                playPendingIntent
            }
            AudioActionState.ACTION_PAUSE ->
            {
                ///pause intent
                val pauseIntent = Intent(serviceContext, AudioService::class.java).apply {
                    action = AudioActionState.ACTION_PAUSE.name
                }
                val pausePendingIntent = PendingIntent.getService(serviceContext, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE)
                pausePendingIntent
            }
            AudioActionState.ACTION_STOP -> {
                ///stop intent
                val stopIntent = Intent(serviceContext, AudioService::class.java).apply {
                    action = AudioActionState.ACTION_STOP.name
                }
                val stopPendingIntent = PendingIntent.getService(serviceContext, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE)
                stopPendingIntent
            }
            AudioActionState.ACTION_NEXT -> {
                ///play next intent
                val playNextIntent = Intent(serviceContext, AudioService::class.java).apply {
                    action = AudioActionState.ACTION_NEXT.name
                }
                val playNextPendingIntent = PendingIntent.getService(serviceContext, 0, playNextIntent, PendingIntent.FLAG_IMMUTABLE)
                playNextPendingIntent
            }
            AudioActionState.ACTION_PREVIOUS -> {
                ///play next intent
                val playPreviousIntent = Intent(serviceContext, AudioService::class.java).apply {
                    action = AudioActionState.ACTION_PREVIOUS.name
                }
                val playPreviousPendingIntent = PendingIntent.getService(serviceContext, 0, playPreviousIntent, PendingIntent.FLAG_IMMUTABLE)
                playPreviousPendingIntent
            }
        }
    }



}