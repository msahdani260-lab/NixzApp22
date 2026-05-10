package com.futuristic.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.futuristic.app.ui.components.GlassTextField
import com.futuristic.app.ui.components.NeonButton
import com.futuristic.app.ui.components.glassmorphism
import com.futuristic.app.ui.theme.DarkBackground
import com.futuristic.app.ui.theme.NeonPink
import com.futuristic.app.viewmodel.MainViewModel
import com.futuristic.app.viewmodel.UiState

@Composable
fun AuthScreen(viewModel: MainViewModel, onNavigateHome: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is UiState.Success) {
            onNavigateHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .glassmorphism(24)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SYSTEM LOGIN",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            GlassTextField(
                value = email,
                onValueChange = { email = it },
                label = "Access ID (Email)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // Menggunakan GlassTextField custom, kita apply visual transformation manual (bisa di-extend di class)
            GlassTextField(
                value = password,
                onValueChange = { password = it },
                label = "Passcode",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            if (loginState is UiState.Error) {
                Text(
                    text = (loginState as UiState.Error).message,
                    color = NeonPink,
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            NeonButton(
                text = "INITIALIZE",
                onClick = { viewModel.login(email, password) },
                isLoading = loginState is UiState.Loading
            )
        }
    }
}
