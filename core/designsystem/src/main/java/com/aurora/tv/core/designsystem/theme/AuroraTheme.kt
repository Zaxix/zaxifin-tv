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
        primaryContainer = AuroraColors.SurfaceCard,
        onPrimaryContainer = AuroraColors.TextPrimary,
        inversePrimary = AuroraColors.AccentAmber,
        secondary = AuroraColors.TextSecondary,
        onSecondary = Color.Black,
        secondaryContainer = AuroraColors.SurfaceElevated,
        onSecondaryContainer = AuroraColors.TextPrimary,
        tertiary = AuroraColors.AccentCyan,
        onTertiary = Color.Black,
        tertiaryContainer = AuroraColors.SurfaceCard,
        onTertiaryContainer = AuroraColors.TextPrimary,
        background = AuroraColors.CanvasBlack,
        onBackground = AuroraColors.TextPrimary,
        surface = AuroraColors.SurfaceElevated,
        onSurface = AuroraColors.TextPrimary,
        surfaceVariant = AuroraColors.SurfaceCard,
        onSurfaceVariant = AuroraColors.TextSecondary,
        surfaceTint = AuroraColors.AccentCyan,
        inverseSurface = AuroraColors.TextPrimary,
        inverseOnSurface = Color.Black,
        error = AuroraColors.AccentRed,
        onError = Color.Black,
        errorContainer = AuroraColors.SurfaceCard,
        onErrorContainer = AuroraColors.AccentRed,
        border = AuroraColors.TextTertiary,
        borderVariant = AuroraColors.TextTertiary,
        scrim = AuroraColors.SurfaceOverlay,
    )

    CompositionLocalProvider(LocalAuroraAccent provides accent) {
        MaterialTheme(
            colorScheme = tvColorScheme,
            content = content,
        )
    }
}

