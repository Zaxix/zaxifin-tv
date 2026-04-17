package com.aurora.tv.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import com.aurora.tv.core.designsystem.component.FocusableCard
import com.aurora.tv.core.designsystem.theme.AuroraColors
import com.aurora.tv.core.designsystem.theme.AuroraType
import com.aurora.tv.domain.model.HomeRow
import com.aurora.tv.domain.model.MediaItem

@Composable
fun HomeScreen(
    onOpenDetail: (String) -> Unit,
    onPlay: (String) -> Unit,
) {
    val rows = remember { demoRows() }
    var focused by remember { mutableStateOf(rows.first().items.first()) }

    Box(modifier = Modifier.fillMaxSize().background(AuroraColors.CanvasBlack)) {
        HeroBackdrop(title = focused.title, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color(0x66000000),
                            Color(0xCC000000),
                            AuroraColors.CanvasBlack,
                        )
                    )
                )
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(40.dp),
            contentPadding = PaddingValues(bottom = 72.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                HeroHeader(
                    item = focused,
                    onPlay = { onPlay(focused.id) },
                    modifier = Modifier.padding(horizontal = 56.dp, vertical = 48.dp),
                )
            }
            items(rows, key = { it.id }) { row ->
                ContentRow(
                    row = row,
                    onItemFocused = { focused = it },
                    onItemClick = { onOpenDetail(it.id) },
                )
            }
        }
    }
}

@Composable
private fun HeroBackdrop(title: String, modifier: Modifier = Modifier) {
    // Placeholder backdrop (offline-friendly). Later: Jellyfin backdrop URL + crossfade.
    Box(
        modifier = modifier.background(
            Brush.radialGradient(
                colors = listOf(Color(0xFF16161B), Color(0xFF000000)),
                radius = 1400f,
            )
        )
    ) {
        Text(
            text = title,
            style = AuroraType.HeroTitle,
            color = AuroraColors.TextTertiary.copy(alpha = 0.10f),
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 56.dp),
        )
    }
}

@Composable
private fun HeroHeader(item: MediaItem, onPlay: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = item.title, style = AuroraType.HeroTitle, color = AuroraColors.TextPrimary)
        Text(
            text = listOfNotNull(item.year?.toString(), item.runtimeMinutes?.let { "${it}m" })
                .joinToString(" · "),
            style = AuroraType.Body,
            color = AuroraColors.TextSecondary,
        )
        Spacer(Modifier.height(10.dp))
        FocusableCard(
            onClick = onPlay,
            aspectRatio = 6f / 1.2f,
            modifier = Modifier.height(88.dp),
        ) { focused ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                    .background(if (focused) AuroraColors.AccentCyan.copy(alpha = 0.20f) else AuroraColors.SurfaceCard)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Text(text = "▶ Play", style = AuroraType.SectionTitle, color = AuroraColors.TextPrimary)
            }
        }
    }
}

@Composable
private fun ContentRow(
    row: HomeRow,
    onItemFocused: (MediaItem) -> Unit,
    onItemClick: (MediaItem) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = row.title,
            style = AuroraType.SectionTitle,
            color = AuroraColors.TextPrimary,
            modifier = Modifier.padding(start = 56.dp, bottom = 14.dp),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 56.dp),
        ) {
            items(row.items, key = { it.id }) { item ->
                FocusableCard(
                    onClick = { onItemClick(item) },
                    onFocusChanged = { if (it) onItemFocused(item) },
                ) { focused ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (focused) AuroraColors.SurfaceCard else Color(0xFF101014))
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart,
                    ) {
                        Text(
                            text = item.title,
                            style = AuroraType.CardTitle,
                            color = AuroraColors.TextPrimary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

private fun demoRows(): List<HomeRow> {
    val items = listOf(
        MediaItem("m1", "Blade Runner 2049", 2017, 164, synopsis = "A new blade runner unearths a secret."),
        MediaItem("m2", "The Expanse", 2015, 45, synopsis = "A conspiracy spans the solar system."),
        MediaItem("m3", "Arrival", 2016, 116, synopsis = "First contact changes everything."),
        MediaItem("m4", "Andor", 2022, 50, synopsis = "Rebellion rises in shadows."),
        MediaItem("m5", "Interstellar", 2014, 169, synopsis = "Love and gravity across stars."),
        MediaItem("m6", "Dune", 2021, 155, synopsis = "Spice, prophecy, and war."),
    )
    return listOf(
        HomeRow("r1", "Continue Watching", items),
        HomeRow("r2", "Recently Added", items.shuffled()),
        HomeRow("r3", "For You", items.shuffled()),
        HomeRow("r4", "Trending", items.shuffled()),
    )
}

