package com.vangelnum.testfirebase

sealed class MenuItems(val title: String, val icon: Int) {
    object Share : MenuItems("Поделиться", R.drawable.ic_baseline_share_24)
    object Contacts : MenuItems("Контакты", R.drawable.ic_baseline_message_24)
    object Stars : MenuItems("Оценить", R.drawable.ic_baseline_star_24)
    object SoundBoard: MenuItems("Zxcursed SoundBoard", R.drawable.soundboard)
    object DrumPad: MenuItems("Zxcursed DrumPad", R.drawable.drumpad)
}
