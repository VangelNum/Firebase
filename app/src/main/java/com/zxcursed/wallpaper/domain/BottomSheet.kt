package com.zxcursed.wallpaper.domain

import com.zxcursed.wallpaper.R

sealed class BottomSheet(val name: String, val icon: Int) {
    object Favourite : BottomSheet("Добавить в избранное", R.drawable.ic_round_favorite_24)
    object FavouriteDelete : BottomSheet("Удалить из избранного", R.drawable.ic_round_favorite_24)
    object Share : BottomSheet("Поделиться", R.drawable.ic_baseline_share_24)
    object Download : BottomSheet("Скачать", R.drawable.ic_baseline_download_24)
}