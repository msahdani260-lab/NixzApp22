package com.futuristic.app.ui.screens

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.futuristic.app.ui.components.NeonButton
import com.futuristic.app.ui.components.glassmorphism
import com.futuristic.app.ui.theme.DarkBackground
import com.futuristic.app.ui.theme.NeonPink
import com.futuristic.app.utils.rememberPermissionState
import com.futuristic.app.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: MainViewModel, onLogout: () -> Unit, onBack: () -> Unit) {
    // Camera permission sebagai contoh handling permission
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SYSTEM SETTINGS", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassmorphism(16)
                    .padding(16.dp)
            ) {
                Text("Permissions Module", color = Color.LightGray, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Camera Status", 
                        color = Color.White, 
                        fontSize = 16.sp
                    )
                    Button(
                        onClick = { cameraPermission.launchPermissionRequest() },
                        enabled = !cameraPermission.isGranted,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (cameraPermission.isGranted) Color.DarkGray else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(if (cameraPermission.isGranted) "GRANTED" else "REQUEST", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            NeonButton(
                text = "TERMINATE SESSION",
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                color = NeonPink
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
