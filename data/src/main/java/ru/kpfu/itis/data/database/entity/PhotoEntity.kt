package ru.kpfu.itis.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: String,
    val url: String,
    val photographer: String,
    val tagsJson: String,
    val liked: Boolean = false,
    val downloadedAt: Long? = null
)