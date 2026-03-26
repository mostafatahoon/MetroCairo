package com.example.cairometroapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = FigmaPrimary,
    secondary = FigmaSecondary,
    tertiary = FigmaBlue,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = FigmaPrimary,
    secondary = FigmaSecondary,
    tertiary = FigmaBlue,
    background = FigmaBg,
    surface = FigmaSurface,
    onPrimary = Color.White,
    onBackground = FigmaTextTitle,
    onSurface = FigmaTextTitle,
    outlineVariant = FigmaBorder
)

@Composable
fun CairoMetroAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
