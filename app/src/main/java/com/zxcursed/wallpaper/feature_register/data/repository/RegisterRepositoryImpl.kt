package com.zxcursed.wallpaper.feature_register.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_register.domain.repository.RegisterRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : RegisterRepository {
    override suspend fun register(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}