package com.zxcursed.wallpaper.feature_favourite.presentation

import com.zxcursed.wallpaper.feature_favourite.domain.model.FavouritePhotosEntity

data class FavouriteState(
    var isLoading: Boolean = false,
    var data: List<FavouritePhotosEntity> = emptyList(),
    var error: String = ""
)