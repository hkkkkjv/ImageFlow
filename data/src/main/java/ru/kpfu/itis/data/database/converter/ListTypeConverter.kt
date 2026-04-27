package ru.kpfu.itis.data.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class ListTypeConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringList(list: List<String>): String = json.encodeToString(list)

    @TypeConverter
    fun toStringList(jsonString: String): List<String> =
        if (jsonString.isBlank()) emptyList() else json.decodeFromString(jsonString)
}