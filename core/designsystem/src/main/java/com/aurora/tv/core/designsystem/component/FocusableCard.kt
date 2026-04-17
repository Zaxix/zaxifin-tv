package com.aurora.tv.core.designsystem.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.aurora.tv.core.designsystem.theme.AuroraColors
import com.aurora.tv.core.designsystem.theme.AuroraMotion

@Composable
fun FocusableCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    aspectRatio: Float = 2f / 3f,
    onFocusChanged: (Boolean) -> Unit = {},
    content: @Composable BoxScope.(focused: Boolean) -> Unit,
) {
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(12.dp)

    val scale by animateFloatAsState(
        targetValue = if (focused) AuroraMotion.FocusScale else 1f,
        animationSpec = AuroraMotion.FocusSpring,
        label = "zaxifin-card-scale",
    )
    val elevation by animateDpAsState(
        targetValue = if (focused) 24.dp else 0.dp,
        label = "zaxifin-card-elev",
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                clip = false
            }
            .shadow(elevation, shape, clip = false)
            .aspectRatio(aspectRatio)
            .clip(shape)
            .onFocusChanged {
                focused = it.isFocused
                onFocusChanged(it.isFocused)
            }
            .focusable()
            .clickable(onClick = onClick)
            .then(
                if (focused) Modifier.border(BorderStroke(3.dp, AuroraColors.FocusRing), shape)
                else Modifier
            )
    ) {
        content(focused)
    }
}

