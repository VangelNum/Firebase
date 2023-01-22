package com.vangelnum.testfirebase.feature_developer.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.vangelnum.testfirebase.feature_developer.domain.model.UserPhotos
import com.vangelnum.testfirebase.feature_developer.domain.repository.DeveloperRepository
import kotlinx.coroutines.tasks.await

class DeveloperRepositoryImpl: DeveloperRepository {
    override suspend fun getUsersPhotos(): List<UserPhotos> {
        val userList = mutableListOf<UserPhotos>()
        val personCollection = Firebase.firestore.collection("users").get().await()
        for (data in personCollection.documents) {
            val userPhoto = data.toObject<UserPhotos>()
            userList.add(userPhoto?: UserPhotos())
        }
        return userList
    }
}