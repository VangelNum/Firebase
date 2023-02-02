package com.zxcursed.wallpaper.feature_register.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.zxcursed.wallpaper.common.Resource

interface RegisterRepository {
    suspend fun register(email: String, password: String): Resource<FirebaseUser>
}