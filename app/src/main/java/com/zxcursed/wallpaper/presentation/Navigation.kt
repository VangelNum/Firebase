package com.zxcursed.wallpaper.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zxcursed.wallpaper.*
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.feature_add_photo.presentation.AddPhotoScreen
import com.zxcursed.wallpaper.feature_contact.presentation.ContactScreen
import com.zxcursed.wallpaper.feature_developer.presentation.DeveloperScreen
import com.zxcursed.wallpaper.feature_developer_join.presentation.DeveloperJoinScreen
import com.zxcursed.wallpaper.feature_favourite.presentation.FavouriteScreen
import com.zxcursed.wallpaper.feature_login.presentattion.LoginScreen
import com.zxcursed.wallpaper.feature_main.presentation.MainScreen
import com.zxcursed.wallpaper.feature_notification.presentation.NotificationScreen
import com.zxcursed.wallpaper.feature_register.presentation.RegisterScreen
import com.zxcursed.wallpaper.feature_watch_photo.presentation.WatchPhotoScreen


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
) {

    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var startDestination = Screens.Register.route
    val uid = currentUser?.uid

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
    val scaffoldStateBottom = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { hostState ->
            SnackbarHost(hostState = hostState) { data ->
                Snackbar(
                    action = {
                        Box(contentAlignment = Alignment.Center) {
                            TextButton(onClick = {
                                data.performAction()
                            }) {
                                Text(text = data.actionLabel.toString())
                            }
                        }
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_favorite_24),
                            "", modifier = Modifier.padding(end = 15.dp)
                        )
                        Text(
                            data.message,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light),
                        )
                    }
                }
            }
        },
        drawerContent = {
            DrawerHeader(auth)
            DrawerBody(navController, scaffoldState)
        },
        drawerElevation = 0.dp,
        topBar = {
            if (showAppBar) {
                if (currentDestination?.route == Screens.WatchPhoto.route + "/{url}") {
                    MyTopBarForWatchScreen(
                        navController = navController,
                        scope = scope,
                        sheetState = sheetState
                    )
                } else {
                    MyTopBar(scaffoldState)
                }
            }
        },
        bottomBar = {
            if (showBottomBar) {
                MyBottomNavigation(items, currentDestination, navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.Register.route) {
                RegisterScreen(auth, navController)
            }
            composable(route = Screens.Login.route) {
                LoginScreen(
                    navController = navController, auth = auth
                )
            }
            composable(route = Screens.Main.route) {
                MainScreen(scaffoldState, navController)
            }
            composable(route = Screens.Favourite.route) {
                FavouriteScreen(navController)
            }
            composable(route = Screens.Add.route) {
                AddPhotoScreen()
            }
            composable(route = Screens.Developer.route) {
                DeveloperScreen()
            }
            composable(route = Screens.DeveloperJoinScreen.route) {
                DeveloperJoinScreen(uid = uid, navController = navController)
            }
            composable(route = Screens.Notification.route) {
                NotificationScreen(navController)
            }
            composable(route = Screens.WatchPhoto.route + "/{url}", arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )) { entry ->
                WatchPhotoScreen(
                    url = entry.arguments?.getString("url"),
                    scaffoldState = scaffoldStateBottom,
                )
            }
            composable(Screens.Contact.route) {
                ContactScreen()
            }

        }
    }
}
