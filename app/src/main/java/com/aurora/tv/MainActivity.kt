package com.aurora.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.aurora.tv.core.designsystem.theme.AuroraTheme
import com.aurora.tv.navigation.AuroraNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            AuroraTheme {
                CompositionLocalProvider {
                    AuroraNavGraph()
                }
            }
        }
    }
}

