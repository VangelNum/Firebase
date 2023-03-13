package com.zxcursed.wallpaper.feature_add_photo.presentation

import androidx.compose.runtime.Composable

data class TabRowItem(
    val title: String,
    val icon: Int,
    val screen: @Composable () -> Unit,
)