package com.vangelnum.testfirebase.feature_developer.presentation

import com.vangelnum.testfirebase.feature_developer.domain.model.UserPhotos

data class DeveloperState(
    var isLoading: Boolean = false,
    var error: String = "",
    var data: List<UserPhotos> = emptyList(),
)
