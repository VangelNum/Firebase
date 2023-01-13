package com.vangelnum.testfirebase

sealed class Screens(val route: String, val icon: Int, val title: String) {
    object Register : Screens("register", R.drawable.ic_round_favorite_24, "")
    object Login : Screens("login", R.drawable.ic_round_favorite_24, "")
    object Main : Screens("main", R.drawable.ic_baseline_home_24, "Главная")
    object Favourite: Screens ("favourite", R.drawable.ic_round_favorite_24,"Избранное")
    object Add: Screens ("add",R.drawable.ic_baseline_add_a_photo_24,"Добавить")
}
