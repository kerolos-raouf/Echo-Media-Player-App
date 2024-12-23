package com.example.echoplaymediaplayer.domain.model

import android.net.Uri

data class Audio(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val uri: Uri,
    val dateModified: Long,
    val genre: String? = null,
    val bitrate: String? = null,
    val size: Long? = null,
    val isFavorite: Boolean = false,
    val playCount: Int = 0,
    val lastPlayed: Long? = null
)
