package com.zxcursed.wallpaper.feature_login.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.core.data.Person
import com.zxcursed.wallpaper.feature_login.domain.repository.LoginRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : LoginRepository {
    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }


    override suspend fun saveData(person: Person) {
        try {
            val collection = fireStore.collection("usersdata")
            collection.add(person).await()
        } catch (e: Exception) {
            Log.d("tag","error ${e.message.toString()}")
            e.printStackTrace()
        }
    }
}