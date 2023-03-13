package com.zxcursed.wallpaper.feature_developer.domain.model

data class UserPhotos(
    val email: String = "",
    val score: Int = 0,
    val url: MutableList<String> = mutableListOf(),
    val userId: String = "",
)

