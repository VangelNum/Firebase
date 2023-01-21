package com.vangelnum.testfirebase.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vangelnum.testfirebase.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    myViewModel: MainViewModel,
) {
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
        Screens.Notification,
        Screens.Add
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var showAppBar by rememberSaveable {
        mutableStateOf(true)
    }
    var showBottomBar by rememberSaveable {
        mutableStateOf(true)
    }

    when (navBackStackEntry?.destination?.route) {
        Screens.Login.route -> {
            showAppBar = false
            showBottomBar = false
        }
        Screens.Register.route -> {
            showAppBar = false
            showBottomBar = false
        }
        else -> {
            showAppBar = true
            showBottomBar = true
        }
    }


    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    Scaffold(
        topBar = {
            if (showAppBar) {
                if (currentDestination?.route == Screens.WatchPhoto.route + "/{url}") {
                    MyTopBarForWatchScreen(navController = navController,
                        scope = scope,
                        sheetState = sheetState)
                } else {
                    MyTopBar(navController, context, uid)
                }
            }
        },
        bottomBar = {
            if (showBottomBar) {
                MyBottomNavigation(items, currentDestination, navController)
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
                MainScreen(viewModel = myViewModel, navController)
            }
            composable(route = Screens.Favourite.route) {
                FavouriteScreen(viewModel = myViewModel)
            }
            composable(route = Screens.Add.route) {
                AddPhotoScreen(auth = auth)
            }
            composable(route = Screens.Developer.route) {
                DeveloperScreen(viewModel = myViewModel, auth = auth)
            }
            composable(route = Screens.Notification.route) {
                NotificationScreen()
            }
            composable(route = Screens.WatchPhoto.route + "/{url}", arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )) { entry ->
                WatchPhotoScreen(
                    url = entry.arguments?.getString("url"),
                    scaffoldState = scaffoldState,
                    viewModel = myViewModel
                )
            }

        }
    }
}
