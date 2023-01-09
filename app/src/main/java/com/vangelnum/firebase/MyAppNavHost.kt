package com.vangelnum.firebase

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vangelnum.firebase.ui.Screens

@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
) {

    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var startDestination = Screens.Register.route
    if (currentUser != null) {
        startDestination = Screens.Main.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screens.Register.route) {
            RegisterScreen(auth, onNavigateToLogin = { navController.navigate(route = Screens.Login.route) })
        }
        composable(route = Screens.Login.route) {
            Text(text = "just check")
        }
    }
}