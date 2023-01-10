package com.vangelnum.firebasee

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Navigataion(
    navController: NavHostController = rememberNavController(),
) {
    val myViewModel: MainViewModel = viewModel()

    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var startDestination = Screens.Register.route
    if (currentUser != null) {
        startDestination = Screens.Main.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screens.Register.route) {
            RegisterScreen(auth,
                onNavigateToLogin = { navController.navigate(route = Screens.Login.route) },
                onNavigateToMain = { navController.navigate(route = Screens.Main.route) })
        }
        composable(route = Screens.Login.route) {
            LoginScreen(onRegisterScreen = { navController.navigate(route = Screens.Register.route) },
                { navController.navigate(route = Screens.Main.route) }, auth)
        }
        composable(route = Screens.Main.route) {
            MainScreen(myViewModel, auth)
        }

    }
}