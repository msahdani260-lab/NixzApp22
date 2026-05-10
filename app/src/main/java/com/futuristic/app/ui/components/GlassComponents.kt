package com.futuristic.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.futuristic.app.ui.theme.GlassBorder
import com.futuristic.app.ui.theme.GlassWhite
import com.futuristic.app.ui.theme.NeonCyan
import com.futuristic.app.ui.theme.NeonPink

// Modifier kustom untuk efek kaca (Glassmorphism)
fun Modifier.glassmorphism(cornerRadius: Int = 16) = this
    .clip(RoundedCornerShape(cornerRadius.dp))
    .background(Brush.linearGradient(listOf(GlassWhite, Color(0x05FFFFFF))))
    .border(
        width = 1.dp,
        brush = Brush.linearGradient(listOf(GlassBorder, Color.Transparent)),
        shape = RoundedCornerShape(cornerRadius.dp)
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.LightGray) },
        modifier = modifier
            .fillMaxWidth()
            .glassmorphism(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = NeonCyan,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = NeonCyan,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun NeonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    color: Color = NeonCyan
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = color,
                spotColor = color
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
        } else {
            Text(
                text = text,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }
    }
}
