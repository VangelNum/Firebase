package com.vangelnum.testfirebase

data class NewPhotos(
    val arrayImages: List<String> = listOf(),
)

data class UserPhotos(
    val url: List<String> = listOf()
)