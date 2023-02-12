package com.zxcursed.wallpaper.feature_add_photo.domain

import android.net.Uri

interface AddPhotoRepository {
    suspend fun addPhoto(textValue: String)

    suspend fun addPhotoToFirestorage(selectedPicUri: Uri?)
}