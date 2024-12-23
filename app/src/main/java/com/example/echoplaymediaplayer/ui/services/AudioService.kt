package com.example.echoplaymediaplayer.ui.services

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.echoplaymediaplayer.R
import com.example.echoplaymediaplayer.domain.audio_service_helper.AudioServiceHelper
import com.example.echoplaymediaplayer.domain.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AudioService : Service() {

    @Inject lateinit var repository: Repository
    @Inject lateinit var audioServiceHelper: AudioServiceHelper
    @Inject lateinit var notificationHandler: NotificationHandler
    private lateinit var exoPlayer : ExoPlayer
    private val musicChannelId = "music_channel"
    private val notificationId = 1
    private var currentAudioProgress : Long = 0
    private var currentAudioUri = ""
    private var currentAudioIndex = -1

    companion object{
        const val SONG_INDEX = "song_index"
    }

    enum class AudioActionState {
        ACTION_PLAY,
        ACTION_PAUSE,
        ACTION_STOP,
        ACTION_NEXT,
        ACTION_PREVIOUS
    }

    override fun onCreate() {
        super.onCreate()

        exoPlayer = ExoPlayer.Builder(this).build()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val action = intent?.action
        (intent?.getIntExtra(SONG_INDEX,-1) ?: -1).takeIf { it != -1 }?.let {
            currentAudioIndex = it
        }
        Log.d("Kerolos", "onStartCommand: $action - $currentAudioIndex")

        when(action)
        {
            AudioActionState.ACTION_PLAY.name -> {
                currentAudioIndex.takeIf { it != -1 }?.let {index->
                    val currentAudio = repository.getAudioAtIndex(index)
                    if (currentAudio.uri.toString() != currentAudioUri) currentAudioProgress = 0
                    playAudio(currentAudio.uri.toString())
                }
            }
            AudioActionState.ACTION_PAUSE.name -> {
                pauseAudio()
            }
            AudioActionState.ACTION_STOP.name -> {
                stopAudio()
            }
            AudioActionState.ACTION_NEXT.name -> {
                audioServiceHelper.playNextAudio()
            }
            AudioActionState.ACTION_PREVIOUS.name -> {
                audioServiceHelper.playPreviousAudio()
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    private fun playAudio(songUrl : String){
        currentAudioUri = songUrl
        val songUri = Uri.parse(songUrl)
        val mediaItem = MediaItem.fromUri(songUri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
        exoPlayer.seekTo(currentAudioProgress)
        startForegroundNotification(true,musicChannelId)
    }

    private fun pauseAudio(){
        if (exoPlayer.isPlaying) {
            currentAudioProgress = exoPlayer.currentPosition
            exoPlayer.pause()
            startForegroundNotification(false,musicChannelId)
        }
    }

    private fun stopAudio()
    {
        if(exoPlayer.isPlaying)
        {
            exoPlayer.stop()
        }
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? = null



    private fun startForegroundNotification(isPlaying : Boolean,channelId : String){
        Log.d("Kerolos", "startForegroundNotification: $isPlaying")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationHandler.createNotificationChannel(
                channelId,
                "Music Player"
            )
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Music Player")
            .setSmallIcon(R.drawable.music_image)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.icon_previous, "Previous",  notificationHandler.getPendingIntent(this,AudioActionState.ACTION_PREVIOUS))
            .addAction(
                if(isPlaying) R.drawable.icon_pause else R.drawable.icon_play,
                if(isPlaying) "Pause" else "Play",
                if (isPlaying)   notificationHandler.getPendingIntent(this,AudioActionState.ACTION_PAUSE) else   notificationHandler.getPendingIntent(this,AudioActionState.ACTION_PLAY))
            .addAction(R.drawable.icon_stop, "Stop",  notificationHandler.getPendingIntent(this,AudioActionState.ACTION_STOP))
            .addAction(R.drawable.icon_next, "Next",  notificationHandler.getPendingIntent(this,AudioActionState.ACTION_NEXT))
            .setOngoing(true)
            .setDeleteIntent(null)
            .setStyle(androidx.media.app.NotificationCompat.DecoratedMediaCustomViewStyle())
            .build()

        startForeground(notificationId, notification)
    }



}