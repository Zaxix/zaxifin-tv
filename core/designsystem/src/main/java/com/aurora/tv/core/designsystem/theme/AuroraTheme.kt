package com.aurora.tv.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ColorScheme
import androidx.tv.material3.MaterialTheme

data class AuroraAccent(
    val focusRing: Color = AuroraColors.FocusRing,
    val progress: Color = AuroraColors.AccentCyan,
)

val LocalAuroraAccent = staticCompositionLocalOf { AuroraAccent() }

@Composable
fun AuroraTheme(
    accent: AuroraAccent = AuroraAccent(),
    content: @Composable () -> Unit,
) {
    val tvColorScheme = ColorScheme(
        primary = AuroraColors.TextPrimary,
        onPrimary = Color.Black,
        secondary = AuroraColors.TextSecondary,
        onSecondary = Color.Black,
        background = AuroraColors.CanvasBlack,
        onBackground = AuroraColors.TextPrimary,
        surface = AuroraColors.SurfaceElevated,
        onSurface = AuroraColors.TextPrimary,
        error = AuroraColors.AccentRed,
        onError = Color.Black,
    )

    CompositionLocalProvider(LocalAuroraAccent provides accent) {
        MaterialTheme(
            colorScheme = tvColorScheme,
            content = content,
        )
    }
}

