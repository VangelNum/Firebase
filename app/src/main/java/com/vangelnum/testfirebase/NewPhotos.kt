package com.vangelnum.testfirebase

data class UserPhotos(
    val email: String = "",
    val score: Int = 0,
    val url: MutableList<String> = mutableListOf(),
    val userId: String = "",
)