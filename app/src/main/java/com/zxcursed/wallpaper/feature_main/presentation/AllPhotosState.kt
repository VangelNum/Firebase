package com.zxcursed.wallpaper.feature_main.presentation

import com.zxcursed.wallpaper.feature_main.domain.model.NewPhotos

data class AllPhotosState(
    var isLoading: Boolean = false,
    var data: NewPhotos? = NewPhotos(emptyList()),
    var error: String = "",
)