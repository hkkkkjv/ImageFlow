package ru.kpfu.itis.data.settings

data class AppSettings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val imageQuality: ImageQuality = ImageQuality.ORIGINAL,
    val cacheLimit: Int = 100
)