package com.vangelnum.testfirebase.feature_developer.domain.repository

import com.vangelnum.testfirebase.common.Resource
import com.vangelnum.testfirebase.feature_developer.domain.model.UserPhotos
import kotlinx.coroutines.flow.Flow

interface DeveloperRepository {
    suspend fun getUsersPhotos():Flow<Resource<List<UserPhotos>>>

    suspend fun addUsersPhotosFromDeveloper(onePhoto: String, collectPhotos: UserPhotos)

    suspend fun deleteUsersPhotosFromDeveleoper(onePhoto: String, collectPhotos: UserPhotos)
}