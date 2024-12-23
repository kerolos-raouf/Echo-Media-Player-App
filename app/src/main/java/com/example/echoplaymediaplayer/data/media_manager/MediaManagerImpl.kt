package com.example.echoplaymediaplayer.data.media_manager

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import com.example.echoplaymediaplayer.domain.model.Audio
import com.example.echoplaymediaplayer.domain.util.OrderType
import com.example.echoplaymediaplayer.domain.util.SongsOrder
import javax.inject.Inject

class MediaManagerImpl @Inject constructor(
    private val application: Application
) : MediaManager {

    override fun getAllAudio(
        songsOrder: SongsOrder
    ) : List<Audio>{
        try {
            val audioList = mutableListOf<Audio>()
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.RELATIVE_PATH,
                MediaStore.Audio.Media.GENRE,
                MediaStore.Audio.Media.BITRATE,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATE_MODIFIED,
            )

            val cursor = application.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                getSortOrder(songsOrder)//MediaStore.Audio.Media.DATE_ADDED + " DESC"
            )

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val albumColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                val pathColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.RELATIVE_PATH)
                val genreColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE)
                val bitrateColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.BITRATE)
                val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
                val dateModifiedColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)

                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val title = it.getString(titleColumn)
                    val artist = it.getString(artistColumn)
                    val duration = it.getLong(durationColumn)
                    val album = it.getString(albumColumn)
                    val songUri = ContentUris.withAppendedId(uri,id)
                    val genre = it.getString(genreColumn)
                    val bitrate = it.getString(bitrateColumn)
                    val size = it.getLong(sizeColumn)
                    val dateModified = it.getString(dateModifiedColumn)

                    audioList.add(Audio(
                        id = id,
                        title = title,
                        artist = artist,
                        album =  album,
                        duration =duration,
                        dateModified = dateModified.toLongOrNull() ?: System.currentTimeMillis(),
                        uri = songUri,
                        genre = genre,
                        bitrate = bitrate,
                        size = size,
                    ))
                }
            }
            return audioList
        }catch (ex : Exception){
            throw ex
        }
    }

    private fun getSortOrder(songsOrder: SongsOrder) : String{
         when(songsOrder.orderType){
            is OrderType.Ascending -> {
                return when(songsOrder){
                    is SongsOrder.SongName -> MediaStore.Audio.Media.TITLE + " ASC"
                    is SongsOrder.Date -> MediaStore.Audio.Media.DATE_ADDED + " ASC"
                    is SongsOrder.Duration -> MediaStore.Audio.Media.DURATION + " ASC"
                    is SongsOrder.Size -> MediaStore.Audio.Media.SIZE + " ASC"
                    is SongsOrder.AlbumName -> MediaStore.Audio.Media.ALBUM + " ASC"
                    is SongsOrder.ArtistName -> MediaStore.Audio.Media.ARTIST + " ASC"
                }
            }
            is OrderType.Descending -> {
                return when(songsOrder){
                    is SongsOrder.SongName -> MediaStore.Audio.Media.TITLE + " DESC"
                    is SongsOrder.Date -> MediaStore.Audio.Media.DATE_ADDED + " DESC"
                    is SongsOrder.Duration -> MediaStore.Audio.Media.DURATION + " DESC"
                    is SongsOrder.Size -> MediaStore.Audio.Media.SIZE + " DESC"
                    is SongsOrder.AlbumName -> MediaStore.Audio.Media.ALBUM + " DESC"
                    is SongsOrder.ArtistName -> MediaStore.Audio.Media.ARTIST + " DESC"
                }
            }
        }
    }
}