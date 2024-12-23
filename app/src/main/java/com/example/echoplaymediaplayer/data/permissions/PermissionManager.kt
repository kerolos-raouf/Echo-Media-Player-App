package com.example.echoplaymediaplayer.data.permissions

import androidx.activity.result.ActivityResultLauncher

interface PermissionManager {
    fun isPermissionGranted(permission: String): Boolean
    suspend fun requestPermission(permission: String,
                                  onGranted: () -> Unit,
                                  onDenied: () -> Unit)

    fun initializeLauncher(launcher: ActivityResultLauncher<String>)
    fun handlePermissionResult(isGranted: Boolean)
}