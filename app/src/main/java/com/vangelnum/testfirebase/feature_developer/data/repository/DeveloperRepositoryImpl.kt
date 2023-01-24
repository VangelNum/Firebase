package com.vangelnum.testfirebase.feature_developer.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vangelnum.testfirebase.feature_developer.domain.model.UserPhotos
import com.vangelnum.testfirebase.feature_developer.domain.repository.DeveloperRepository

class DeveloperRepositoryImpl : DeveloperRepository {
    override suspend fun getUsersPhotos(): List<UserPhotos> {
        val userList = mutableListOf<UserPhotos>()
        val personCollection = Firebase.firestore.collection("users")
        personCollection.addSnapshotListener { value, error ->
            error?.let {

            }
            value?.let {
                for (data in it.documents) {
                    val userPhoto = data.toObject(UserPhotos::class.java)
                    userList.add(userPhoto!!)
                }
            }
        }
        return userList
    }
}