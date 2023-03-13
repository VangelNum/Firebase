package com.zxcursed.wallpaper.feature_add_photo.domain

import android.content.Context
import android.net.Uri

interface AddPhotoRepository {
    suspend fun addPhoto(textValue: String, context: Context)

    suspend fun addPhotoToFirestorage(selectedPicUri: Uri?, context: Context)
}