package com.vangelnum.testfirebase.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.vangelnum.testfirebase.*
import com.vangelnum.testfirebase.R
import com.vangelnum.testfirebase.feature_developer.presentation.DeveloperScreen
import com.vangelnum.testfirebase.feature_favourite.presentation.FavouriteScreen
import com.vangelnum.testfirebase.feature_main.presentation.MainScreen
import com.vangelnum.testfirebase.feature_notification.presentation.NotificationScreen


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
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
            DrawerBody(navController)
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
                    MyTopBar(navController, context, uid, scaffoldState)
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
                RegisterScreen(auth,
                    onNavigateToLogin = { navController.navigate(route = Screens.Login.route) },
                    onNavigateToMain = { navController.navigate(route = Screens.Main.route) })
            }
            composable(route = Screens.Login.route) {
                LoginScreen(
                    onNavigateToRegister = { navController.navigate(route = Screens.Register.route) },
                    { navController.navigate(route = Screens.Main.route) }, auth
                )
            }
            composable(route = Screens.Main.route) {
                MainScreen(scaffoldState, navController)
            }
            composable(route = Screens.Favourite.route) {
                FavouriteScreen(navController)
            }
            composable(route = Screens.Add.route) {
                AddPhotoScreen(auth = auth)
            }
            composable(route = Screens.Developer.route) {
                DeveloperScreen()
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
                    scaffoldState = scaffoldStateBottom
                )
            }

        }
    }
}
