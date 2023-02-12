package com.zxcursed.wallpaper.domain

import androidx.compose.runtime.Composable

data class TabRowItem(
    val title: String,
    val icon: Int,
    val screen: @Composable () -> Unit,
)