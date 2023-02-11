package com.zxcursed.wallpaper.feature_add_photo.domain

interface AddPhotoRepository {
    suspend fun addPhoto(textValue: String)
}