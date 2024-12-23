package com.example.echoplaymediaplayer.domain.use_cases

import com.example.echoplaymediaplayer.domain.repository.Repository
import com.example.echoplaymediaplayer.domain.model.Audio
import com.example.echoplaymediaplayer.domain.util.OrderType
import com.example.echoplaymediaplayer.domain.util.Result
import com.example.echoplaymediaplayer.domain.util.SongsOrder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAudioUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(
        songsOrder: SongsOrder = SongsOrder.Date(OrderType.Descending)
    ) : Flow<Result<List<Audio>>> {
        return repository.getAllAudio(songsOrder)
    }

}