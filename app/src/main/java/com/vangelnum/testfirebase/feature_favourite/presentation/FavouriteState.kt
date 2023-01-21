package com.vangelnum.testfirebase.feature_favourite.presentation

import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity

data class FavouriteState(
    var isLoading: Boolean = false,
    var data: List<FavouritePhotosEntity> = emptyList(),
    var error: String = ""
)