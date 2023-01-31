package com.zxcursed.wallpaper.feature_main.domain.repository

import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_main.domain.model.NewPhotos
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getAllPhotos(): Flow<Resource<NewPhotos>>
}