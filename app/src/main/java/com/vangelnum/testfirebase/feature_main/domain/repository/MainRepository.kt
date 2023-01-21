package com.vangelnum.testfirebase.feature_main.domain.repository

import com.vangelnum.testfirebase.feature_main.domain.model.NewPhotos
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getAllPhotos(): Flow<NewPhotos>
}