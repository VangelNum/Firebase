package com.vangelnum.testfirebase.feature_developer.domain.repository

import com.vangelnum.testfirebase.common.Resource
import com.vangelnum.testfirebase.feature_developer.domain.model.UserPhotos
import kotlinx.coroutines.flow.Flow

interface DeveloperRepository {
    suspend fun getUsersPhotos():Flow<Resource<List<UserPhotos>>>
}