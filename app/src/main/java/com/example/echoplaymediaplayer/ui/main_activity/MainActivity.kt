package com.example.echoplaymediaplayer.ui.main_activity

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.echoplaymediaplayer.data.permissions.PermissionManager
import com.example.echoplaymediaplayer.ui.navigation.NavHostSetup
import com.example.echoplaymediaplayer.ui.spalsh_screen.SplashScreen
import com.example.echoplaymediaplayer.ui.theme.EchoPlayMediaPlayerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var permissionManager: PermissionManager

    private val activityResult = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted->
        permissionManager.handlePermissionResult(isGranted)
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager.initializeLauncher(activityResult)
        setContent {
            EchoPlayMediaPlayerTheme {
                NavHostSetup(permissionManager)
            }
        }
    }
}

