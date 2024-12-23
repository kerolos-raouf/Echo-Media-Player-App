package com.example.echoplaymediaplayer.domain.util

sealed class SongsOrder(val orderType: OrderType) {
    class SongName(orderType: OrderType) : SongsOrder(orderType)
    class Date(orderType: OrderType) : SongsOrder(orderType)
    class Duration(orderType: OrderType) : SongsOrder(orderType)
    class Size(orderType: OrderType) : SongsOrder(orderType)
    class AlbumName(orderType: OrderType) : SongsOrder(orderType)
    class ArtistName(orderType: OrderType) : SongsOrder(orderType)

    fun copySongsOrder(orderType: OrderType): SongsOrder {
        return when (this) {
            is SongName -> SongName(orderType)
            is Date -> Date(orderType)
            is Duration -> Duration(orderType)
            is Size -> Size(orderType)
            is AlbumName -> AlbumName(orderType)
            is ArtistName -> ArtistName(orderType)
        }
    }
}