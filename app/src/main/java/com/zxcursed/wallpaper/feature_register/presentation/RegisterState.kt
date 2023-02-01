package com.zxcursed.wallpaper.feature_register.presentation

import com.google.firebase.auth.FirebaseUser

data class RegisterState(
    val isLoading: Boolean = true,
    val data: FirebaseUser? = null,
    val error: String = ""
)
