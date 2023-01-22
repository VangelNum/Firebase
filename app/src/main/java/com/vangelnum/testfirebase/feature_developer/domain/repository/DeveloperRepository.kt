package com.vangelnum.testfirebase.feature_developer.domain.repository

import com.vangelnum.testfirebase.feature_developer.domain.model.UserPhotos

interface DeveloperRepository {
    suspend fun getUsersPhotos(): List<UserPhotos>
}