package ru.kpfu.itis.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PexelsPhotoDto(
    val id: Long,
    val url: String,
    val photographer: String,
    val src: PhotoSourceDto,
    val liked: Boolean = false
)