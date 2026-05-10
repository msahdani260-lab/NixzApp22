package com.futuristic.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- Colors ---
val DarkBackground = Color(0xFF070B14)
val NeonCyan = Color(0xFF00F0FF)
val NeonPink = Color(0xFFFF003C)
val GlassWhite = Color(0x1AFFFFFF) // 10% White
val GlassBorder = Color(0x33FFFFFF) // 20% White

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonPink,
    background = DarkBackground,
    surface = DarkBackground,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun FuturisticTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Support auto dark/light (defaulting to dark look for neon)
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme // Mengunci ke mode gelap untuk estetika neon
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
