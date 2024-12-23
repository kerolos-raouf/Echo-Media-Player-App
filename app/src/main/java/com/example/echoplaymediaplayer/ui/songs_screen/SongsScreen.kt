package com.example.echoplaymediaplayer.ui.songs_screen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echoplaymediaplayer.R
import com.example.echoplaymediaplayer.data.permissions.PermissionManager
import com.example.echoplaymediaplayer.domain.model.Audio
import com.example.echoplaymediaplayer.domain.util.OrderType
import com.example.echoplaymediaplayer.domain.util.Result
import com.example.echoplaymediaplayer.domain.util.SongsOrder
import com.example.echoplaymediaplayer.domain.util.getDateWithCustomFormat
import com.example.echoplaymediaplayer.ui.services.AudioService
import com.example.echoplaymediaplayer.ui.theme.robotoFontFamily
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SongsScreen(
    permissionManager: PermissionManager,
    viewModel: SongsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    if (permissionManager.isPermissionGranted(Manifest.permission.READ_MEDIA_AUDIO)){
        viewModel.onEvent(SongsScreenEvent.GetAllSongs(SongsOrder.Date(OrderType.Descending)))
    }else{
        LaunchedEffect(key1 = Unit) {
            scope.launch {
                permissionManager.requestPermission(Manifest.permission.READ_MEDIA_AUDIO,{
                    viewModel.onEvent(SongsScreenEvent.GetAllSongs(SongsOrder.Date(OrderType.Descending)))
                },{

                })
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            when(state.value.audioListResult){
                is Result.Error -> {
                    Log.d("Kerolos", "SongsScreen: ${(state.value.audioListResult as Result.Error).message}")
                }
                Result.Loading -> {
                    CircularProgressIndicator()
                }
                is Result.Success -> {
                    val data = (state.value.audioListResult as Result.Success<List<Audio>>).data
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent)
                    ) {
                        itemsIndexed(data){index,audio->
                            CustomAudioItem(
                                audio = audio,
                                height = 80
                            ){
                                viewModel.onEvent(SongsScreenEvent.PlaySpecificAudio(index))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomAudioItem(
    audio: Audio,
    height: Int = 70,
    onClick : () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(Color.Transparent)
            .padding(10.dp)
            .clickable {
                onClick()
            }
    ){
        Row(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
            ){
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.music_image),
                    contentDescription = "")
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Transparent)
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(3.dp),
                    text = audio.title,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = robotoFontFamily,
                    maxLines = 1
                )
                Text(
                    text = "${audio.artist} - ${audio.album}",
                    fontSize = 13.sp,
                    color = Color.LightGray,
                    fontFamily = robotoFontFamily
                )
            }

            Text(
                text = getDateWithCustomFormat(audio.dateModified,"dd-MM"),
                fontSize = 13.sp,
                color = Color.LightGray,
                fontFamily = robotoFontFamily
            )

            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "",
                tint = Color.LightGray
            )

        }
    }
}