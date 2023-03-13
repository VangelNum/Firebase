package com.zxcursed.wallpaper.core.presentation.drawer_layout

import androidx.annotation.StringRes
import com.zxcursed.wallpaper.R

sealed class DrawerItems(@StringRes val title: Int, val icon: Int) {
    object Share : DrawerItems(R.string.share, R.drawable.ic_baseline_share_24)
    object Contacts : DrawerItems(R.string.contact, R.drawable.ic_baseline_message_24)
    object Stars : DrawerItems(R.string.stars, R.drawable.ic_baseline_star_24)
    object SoundBoard : DrawerItems(R.string.zxcursed_soundboard, R.drawable.soundboard)
    object DrumPad : DrawerItems(R.string.zxcursed_drumpad, R.drawable.drumpad)
    object Exit : DrawerItems(R.string.exit, R.drawable.ic_baseline_exit_to_app_24)
}
