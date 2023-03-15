package com.zxcursed.wallpaper.feature_add_photo.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

data class TabRowItem(
    @StringRes val title: Int,
    val icon: Int,
    val screen: @Composable () -> Unit,
)