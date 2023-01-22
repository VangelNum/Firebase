package com.vangelnum.testfirebase.feature_main.domain.repository

import com.vangelnum.testfirebase.feature_main.domain.model.NewPhotos

interface MainRepository {
    suspend fun getAllPhotos(): NewPhotos
}