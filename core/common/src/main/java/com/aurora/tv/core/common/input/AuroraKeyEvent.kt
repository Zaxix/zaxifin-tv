package com.aurora.tv.core.common.input

import android.view.KeyEvent
import androidx.compose.ui.input.key.KeyEvent as ComposeKeyEvent

sealed interface AuroraKey {
    data object Select : AuroraKey
    data object Back : AuroraKey
    data object Up : AuroraKey
    data object Down : AuroraKey
    data object Left : AuroraKey
    data object Right : AuroraKey
    data object PlayPause : AuroraKey
    data object FastForward : AuroraKey
    data object Rewind : AuroraKey
    data object Menu : AuroraKey
}

fun ComposeKeyEvent.toAurora(): AuroraKey? = when (nativeKeyEvent.keyCode) {
    KeyEvent.KEYCODE_DPAD_CENTER,
    KeyEvent.KEYCODE_ENTER,
    -> AuroraKey.Select

    KeyEvent.KEYCODE_BACK -> AuroraKey.Back
    KeyEvent.KEYCODE_DPAD_UP -> AuroraKey.Up
    KeyEvent.KEYCODE_DPAD_DOWN -> AuroraKey.Down
    KeyEvent.KEYCODE_DPAD_LEFT -> AuroraKey.Left
    KeyEvent.KEYCODE_DPAD_RIGHT -> AuroraKey.Right

    KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE,
    KeyEvent.KEYCODE_MEDIA_PLAY,
    KeyEvent.KEYCODE_MEDIA_PAUSE,
    -> AuroraKey.PlayPause

    KeyEvent.KEYCODE_MEDIA_FAST_FORWARD -> AuroraKey.FastForward
    KeyEvent.KEYCODE_MEDIA_REWIND -> AuroraKey.Rewind
    KeyEvent.KEYCODE_MENU -> AuroraKey.Menu
    else -> null
}

