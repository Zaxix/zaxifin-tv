package com.aurora.tv.feature.detail

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import com.aurora.tv.core.designsystem.component.FocusableCard
import com.aurora.tv.core.designsystem.theme.AuroraColors
import com.aurora.tv.core.designsystem.theme.AuroraType
import com.aurora.tv.domain.model.MediaItem
import com.aurora.tv.domain.model.Ratings

@Composable
fun DetailScreen(
    itemId: String,
    onPlay: (String) -> Unit,
    onBack: () -> Unit,
) {
    val item = remember(itemId) { demoItem(itemId) }
    val ratings = remember(itemId) { demoRatings() }

    Box(modifier = Modifier.fillMaxSize().background(AuroraColors.SurfaceElevated)) {
        // Placeholder backdrop
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1A1A22), AuroraColors.SurfaceElevated, AuroraColors.SurfaceElevated)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 56.dp, vertical = 44.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Text(text = item.title, style = AuroraType.HeroTitle, color = AuroraColors.TextPrimary)
            Text(
                text = listOfNotNull(item.year?.toString(), item.runtimeMinutes?.let { "${it}m" })
                    .joinToString(" · "),
                style = AuroraType.Body,
                color = AuroraColors.TextSecondary,
            )

            RatingsStrip(ratings = ratings)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FocusableCard(
                    onClick = { onPlay(item.id) },
                    aspectRatio = 4f / 1f,
                    modifier = Modifier.height(88.dp),
                ) { focused ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (focused) AuroraColors.AccentCyan.copy(0.22f) else AuroraColors.SurfaceCard),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "▶ Play", style = AuroraType.SectionTitle, color = AuroraColors.TextPrimary)
                    }
                }
                FocusableCard(
                    onClick = onBack,
                    aspectRatio = 4f / 1f,
                    modifier = Modifier.height(88.dp),
                ) { focused ->
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

            Spacer(Modifier.height(10.dp))
            Text(text = "Synopsis", style = AuroraType.SectionTitle, color = AuroraColors.TextPrimary)
            ExpandableSynopsis(text = item.synopsis)
        }
    }
}

@Composable
private fun RatingsStrip(ratings: Ratings, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ratings.imdb?.let {
            RatingBadge(label = "IMDb", value = "%.1f".format(it), tint = AuroraColors.AccentAmber)
        }
        ratings.rtCritic?.let {
            val fresh = it >= 60
            RatingBadge(
                label = if (fresh) "RT" else "RT",
                value = "$it%",
                tint = if (fresh) AuroraColors.AccentGreen else AuroraColors.AccentRed,
                secondary = ratings.rtAudienceProxy?.let { a -> "$a%" },
            )
        }
        ratings.techBadges.forEach { badge ->
            TechBadge(text = badge)
        }
    }
}

@Composable
private fun RatingBadge(label: String, value: String, tint: Color, secondary: String? = null) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, style = AuroraType.Badge, color = tint)
        Text(text = value, style = AuroraType.Caption, color = AuroraColors.TextPrimary)
        secondary?.let {
            Text(text = "/", style = AuroraType.Caption, color = AuroraColors.TextTertiary)
            Text(text = it, style = AuroraType.Caption, color = AuroraColors.TextSecondary)
        }
    }
}

@Composable
private fun TechBadge(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0x1AFFFFFF), RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
    ) {
        Text(text = text, style = AuroraType.Badge, color = AuroraColors.TextSecondary)
    }
}

@Composable
private fun ExpandableSynopsis(text: String) {
    // Minimal version (TV OK toggling will come once player key handling is wired globally)
    Text(
        text = text,
        style = AuroraType.Body,
        color = AuroraColors.TextSecondary,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
    )
}

private fun demoItem(id: String) = MediaItem(
    id = id,
    title = "Blade Runner 2049",
    year = 2017,
    runtimeMinutes = 164,
    synopsis = "Thirty years after the events of the first film, a new blade runner unearths a long-buried secret that has the potential to plunge what's left of society into chaos.",
    genres = listOf("Sci-Fi", "Drama"),
)

private fun demoRatings() = Ratings(
    imdb = 8.0,
    rtCritic = 88,
    rtAudienceProxy = 81,
    techBadges = listOf("4K", "HDR", "ATMOS"),
)

