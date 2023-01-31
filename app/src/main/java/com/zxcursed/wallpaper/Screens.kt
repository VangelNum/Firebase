package com.zxcursed.wallpaper

sealed class Screens(val route: String, val icon: Int, val title: String) {
    object Register : Screens("register", R.drawable.ic_round_favorite_24, "")
    object Login : Screens("login", R.drawable.ic_round_favorite_24, "")

    object Main : Screens("main", R.drawable.ic_baseline_home_24, "Главная")
    object Favourite : Screens("favourite", R.drawable.ic_round_favorite_24, "Избранное")
    object Add : Screens("add", R.drawable.ic_baseline_add_a_photo_24, "Добавить")
    object Notification : Screens("notification", R.drawable.ic_baseline_notifications_24, "Уведомления")

    object Developer : Screens("developer", R.drawable.ic_round_favorite_24, "Разработчик")
    object WatchPhoto : Screens("watch", R.drawable.ic_round_favorite_24, "Смотреть")
    object Contact: Screens("contact",R.drawable.ic_round_favorite_24,"Контакты")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
