package com.vangelnum.testfirebase.feature_main.data.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.vangelnum.testfirebase.feature_main.domain.model.NewPhotos
import com.vangelnum.testfirebase.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainRepositoryImpl : MainRepository {
    override fun getAllPhotos(): Flow<NewPhotos> {
        val myCollection = Firebase.firestore.collection("images").document("tutor")
        val result: Flow<NewPhotos> = MutableStateFlow(NewPhotos(emptyList()))
        try {
            myCollection.addSnapshotListener { querySnapshot, firebaseException ->
                firebaseException?.let {
                    Log.d("Error", it.message.toString())
                }
                querySnapshot?.let { photos ->
                    CoroutineScope(Dispatchers.IO).launch {
                        result.collect {
                            photos.toObject<NewPhotos>()
                        }
                    }

                }
            }
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
        }
        return result
    }
}