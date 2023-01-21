package com.vangelnum.testfirebase

data class NewPhotos(
    val arrayImages: List<String> = listOf(),
)

data class UserPhotos(
    val email: String = "",
    val score: Int = 0,
    val url: MutableList<String> = mutableListOf(),
    val userId: String = "",
)