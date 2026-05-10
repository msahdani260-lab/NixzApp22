package com.futuristic.app.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun rememberPermissionState(permission: String): PermissionState {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasPermission = granted
        }
    )

    return remember(launcher, hasPermission) {
        PermissionState(hasPermission, launcher, permission)
    }
}

class PermissionState(
    val isGranted: Boolean,
    private val launcher: ManagedActivityResultLauncher<String, Boolean>,
    private val permission: String
) {
    fun launchPermissionRequest() {
        launcher.launch(permission)
    }
}
