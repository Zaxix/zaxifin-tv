package com.aurora.tv.feature.player

import android.view.KeyEvent
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.tv.material3.Text
import com.aurora.tv.core.common.input.AuroraKey
import com.aurora.tv.core.common.input.toAurora
import com.aurora.tv.core.designsystem.component.FocusableCard
import com.aurora.tv.core.designsystem.theme.AuroraColors
import com.aurora.tv.core.designsystem.theme.AuroraMotion
import com.aurora.tv.core.designsystem.theme.AuroraType
import kotlinx.coroutines.delay

@Composable
fun PlayerScreen(
    itemId: String,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    // Minimal, local player for now (placeholder stream wiring comes with Jellyfin repo later).
    val player = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_OFF
            playWhenReady = true
        }
    }

    var chromeVisible by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(true) }

    LaunchedEffect(chromeVisible, isPlaying) {
        if (chromeVisible && isPlaying) {
            delay(4000)
            chromeVisible = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onPreviewKeyEvent { event ->
                if (event.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) chromeVisible = true
                when (event.toAurora()) {
                    AuroraKey.Back -> {
                        onBack()
                        true
                    }
                    AuroraKey.PlayPause, AuroraKey.Select -> {
                        isPlaying = !isPlaying
                        player.playWhenReady = isPlaying
                        true
                    }
                    else -> false
                }
            }
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    layoutParams = android.widget.FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    this.player = player
                }
            },
            modifier = Modifier.fillMaxSize(),
        )

        AnimatedVisibility(
            visible = chromeVisible,
            enter = fadeIn(),
            exit = fadeOut(AuroraMotion.OverlayFadeOut),
        ) {
            PlayerChrome(
                itemId = itemId,
                isPlaying = isPlaying,
                onToggle = {
                    isPlaying = !isPlaying
                    player.playWhenReady = isPlaying
                },
                onBack = onBack,
            )
        }
    }
}

@Composable
private fun PlayerChrome(
    itemId: String,
    isPlaying: Boolean,
    onToggle: () -> Unit,
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AuroraColors.SurfaceOverlay)
            .padding(48.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Text(text = "Now Playing", style = AuroraType.SectionTitle, color = AuroraColors.TextPrimary)
            Text(text = "Item: $itemId", style = AuroraType.Body, color = AuroraColors.TextSecondary)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                FocusableCard(onClick = onToggle, aspectRatio = 4f / 1f, modifier = Modifier.height(88.dp)) { focused ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (focused) AuroraColors.AccentCyan.copy(0.22f) else Color(0x14FFFFFF)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = if (isPlaying) "Pause" else "Play",
                            style = AuroraType.SectionTitle,
                            color = AuroraColors.TextPrimary,
                        )
                    }
                }
                FocusableCard(onClick = onBack, aspectRatio = 4f / 1f, modifier = Modifier.height(88.dp)) { focused ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (focused) Color(0x22FFFFFF) else Color(0x14FFFFFF)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "Back", style = AuroraType.SectionTitle, color = AuroraColors.TextPrimary)
                    }
                }
            }
        }
    }
}

