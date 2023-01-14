package com.vangelnum.testfirebase

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
) {
    val myViewModel: MainViewModel = viewModel()

    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var startDestination = Screens.Register.route
    val uid = currentUser?.uid
    val context = LocalContext.current

    if (currentUser != null && currentUser.isEmailVerified) {
        startDestination = Screens.Main.route
    }

    val items = listOf(
        Screens.Main,
        Screens.Favourite,
        Screens.Add
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            TopAppBar {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val myCollection = Firebase.firestore.collection("developer").document(uid!!)
                            val querySnapShot = myCollection.get().await()
                            withContext(Dispatchers.Main) {
                                navController.navigate(route = Screens.Developer.route)
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context,
                                    "Error: ${e.message.toString()}",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_round_favorite_24),
                        contentDescription = "admin_panel")
                }
            }
        },
        bottomBar = {
            if (currentDestination != null) {
                if (currentDestination.route != Screens.Register.route && currentDestination.route != Screens.Login.route) {
                    BottomNavigation {

                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = {
                                    Icon(painter = painterResource(id = screen.icon),
                                        contentDescription = null)
                                },
                                label = { Text(text = screen.title) },
                                selected = currentDestination.hierarchy.any { it.route == screen.route } == true,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }

                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)) {
            composable(route = Screens.Register.route) {
                RegisterScreen(auth,
                    onNavigateToLogin = { navController.navigate(route = Screens.Login.route) },
                    onNavigateToMain = { navController.navigate(route = Screens.Main.route) })
            }
            composable(route = Screens.Login.route) {
                LoginScreen(onNavigateToRegister = { navController.navigate(route = Screens.Register.route) },
                    { navController.navigate(route = Screens.Main.route) }, auth)
            }
            composable(route = Screens.Main.route) {
                MainScreen(myViewModel)
            }
            composable(route = Screens.Favourite.route) {
                FavouriteScreen()
            }
            composable(route = Screens.Add.route) {
                AddPhotoScreen(auth)
            }
            composable(route = Screens.Developer.route) {
                DeveloperScreen(viewModel = myViewModel, auth = auth)
            }

        }
    }
}