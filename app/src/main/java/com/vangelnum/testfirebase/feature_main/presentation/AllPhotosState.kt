package com.vangelnum.testfirebase.feature_main.presentation

import com.vangelnum.testfirebase.feature_main.domain.model.NewPhotos

data class AllPhotosState(
    var isLoading: Boolean = false,
    var data: NewPhotos? = NewPhotos(emptyList()),
    var error: String = "",
)