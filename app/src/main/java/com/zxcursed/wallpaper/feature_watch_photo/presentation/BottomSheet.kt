package com.zxcursed.wallpaper.feature_watch_photo.presentation

import androidx.annotation.StringRes
import com.zxcursed.wallpaper.R

sealed class BottomSheet(@StringRes val name: Int, val icon: Int) {
    object Favourite : BottomSheet(R.string.add_to_favourite, R.drawable.ic_round_favorite_24)
    object FavouriteDelete : BottomSheet(R.string.delete_from_favourite, R.drawable.ic_round_favorite_24)
    object Share : BottomSheet(R.string.share, R.drawable.ic_baseline_share_24)
    object Download : BottomSheet(R.string.download, R.drawable.ic_baseline_download_24)
    object Copy: BottomSheet(R.string.copy,R.drawable.baseline_content_copy_24)
    object Set: BottomSheet(R.string.set,R.drawable.baseline_image_24)
}