package com.vangelnum.testfirebase

import android.content.Context
import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun MyTopBar(
    navController: NavController,
    context: Context = LocalContext.current,
    uid: String?,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    TopAppBar(title = {
        Text(text = "")
    }, navigationIcon = {
        IconButton(onClick = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                contentDescription = "menu")
        }
    }, actions = {
        IconButton(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val myCollection =
                        Firebase.firestore.collection("developer").document(uid!!)
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
    }, elevation = 0.dp)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyTopBarForWatchScreen(
    navController: NavController,
    scope: CoroutineScope,
    sheetState: BottomSheetState,
) {
    TopAppBar(
        elevation = 0.dp,
        title = {},
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "back")
            }
        },
        actions = {
            IconButton(onClick = {
                scope.launch {
                    if (sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                    contentDescription = "more")
            }
        }
    )
}