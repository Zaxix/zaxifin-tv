package com.aurora.tv.domain.model

data class MediaItem(
    val id: String,
    val title: String,
    val year: Int? = null,
    val runtimeMinutes: Int? = null,
    val synopsis: String = "",
    val genres: List<String> = emptyList(),
)

data class HomeRow(
    val id: String,
    val title: String,
    val items: List<MediaItem>,
)

data class Ratings(
    val imdb: Double? = null,
    val rtCritic: Int? = null,
    val rtAudienceProxy: Int? = null,
    val techBadges: List<String> = emptyList(),
)

