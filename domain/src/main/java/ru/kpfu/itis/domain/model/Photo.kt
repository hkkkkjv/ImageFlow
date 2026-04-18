package ru.kpfu.itis.domain.model

import java.time.LocalDateTime

data class Photo(
    val id: PhotoId,
    val url: String,
    val photographer: String,
    val tags: List<String>,
    val liked: Boolean = false,
    val downloadedAt: LocalDateTime? = null
)

@JvmInline
value class PhotoId(val value: String)

@JvmInline
value class PageNumber(val value: Int)