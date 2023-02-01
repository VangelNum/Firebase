package com.zxcursed.wallpaper.feature_developer.domain.repository

import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_developer.domain.model.UserPhotos
import kotlinx.coroutines.flow.Flow

interface DeveloperRepository {
    fun getUsersPhotos(): Flow<Resource<List<UserPhotos>>>

    suspend fun addUsersPhotosFromDeveloper(onePhoto: String, collectPhotos: UserPhotos)

    suspend fun deleteUsersPhotosFromDeveleoper(onePhoto: String, collectPhotos: UserPhotos)
}