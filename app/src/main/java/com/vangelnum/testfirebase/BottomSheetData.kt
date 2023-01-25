package com.vangelnum.testfirebase

sealed class BottomSheetData(val name: String, val icon: Int) {
    object Favourite : BottomSheetData("Добавить в избранное", R.drawable.ic_round_favorite_24)
    object FavouriteDelete : BottomSheetData("Удалить из избранного", R.drawable.ic_round_favorite_24)
    object Share : BottomSheetData("Поделиться", R.drawable.ic_baseline_share_24)
    object Download : BottomSheetData("Скачать", R.drawable.ic_baseline_download_24)
}