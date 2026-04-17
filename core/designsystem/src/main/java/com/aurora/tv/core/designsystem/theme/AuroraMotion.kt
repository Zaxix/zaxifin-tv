package com.aurora.tv.core.designsystem.theme

import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween

object AuroraMotion {
    val FocusScale = 1.08f
    val FocusSpring: SpringSpec<Float> = spring(
        dampingRatio = 0.72f,
        stiffness = 380f,
        visibilityThreshold = Spring.DefaultDisplacementThreshold,
    )

    val PageEnter: TweenSpec<Float> = tween(durationMillis = 380, easing = EaseOutCubic)
    val PageExit: TweenSpec<Float> = tween(durationMillis = 260, easing = EaseInCubic)

    val HeroCrossfade: TweenSpec<Float> = tween(durationMillis = 520, easing = EaseInOutCubic)
    val OverlayFadeOut: TweenSpec<Float> = tween(durationMillis = 300, easing = EaseInCubic)
}

