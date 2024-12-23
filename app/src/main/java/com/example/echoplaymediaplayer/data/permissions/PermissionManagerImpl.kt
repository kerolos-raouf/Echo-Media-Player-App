package com.example.echoplaymediaplayer.data.permissions

import android.app.Application
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class PermissionManagerImpl @Inject constructor(
    private val application: Application,
) : PermissionManager{

    private var launcher: ActivityResultLauncher<String>? = null
    private var onPermissionResult: ((Boolean) -> Unit)? = null

    override fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(application, permission) == PackageManager.PERMISSION_GRANTED
    }


    override suspend fun requestPermission(
        permission: String,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        if (isPermissionGranted(permission)) {
            onGranted()
        } else {
            val isGranted = suspendCancellableCoroutine { cont ->
                onPermissionResult = { granted ->
                    cont.resume(granted)
                }

                launcher?.launch(permission)
                    ?: cont.resume(false)
            }

            if (isGranted) {
                onGranted()
            } else {
                onDenied()
            }
        }
    }

    override fun initializeLauncher(launcher: ActivityResultLauncher<String>) {
        this.launcher = launcher
    }


    override fun handlePermissionResult(isGranted: Boolean) {
        onPermissionResult?.invoke(isGranted)
        onPermissionResult = null
    }

}