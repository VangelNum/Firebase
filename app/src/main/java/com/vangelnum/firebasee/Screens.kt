package com.vangelnum.firebasee

sealed class Screens(val route: String) {
    object Register : Screens("register")
    object Login : Screens("login")
    object Main : Screens("main")
}
