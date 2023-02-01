package com.zxcursed.wallpaper.feature_login.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.zxcursed.wallpaper.common.Resource

interface LoginRepository {
    suspend fun login(email: String, password: String) : Resource<FirebaseUser>
}