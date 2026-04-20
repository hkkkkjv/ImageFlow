package ru.kpfu.itis.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PhotoSourceDto(
    val original: String,
    val large: String,
    val medium: String,
    val small: String
)