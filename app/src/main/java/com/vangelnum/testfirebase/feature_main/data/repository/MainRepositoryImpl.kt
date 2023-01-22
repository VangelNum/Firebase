package com.vangelnum.testfirebase.feature_main.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.vangelnum.testfirebase.feature_main.domain.model.NewPhotos
import com.vangelnum.testfirebase.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.tasks.await

class MainRepositoryImpl : MainRepository {
    override suspend fun getAllPhotos(): NewPhotos {
        val myCollection = Firebase.firestore.collection("images").document("tutor")
        return myCollection.get().await().toObject<NewPhotos>()!!
    }
}