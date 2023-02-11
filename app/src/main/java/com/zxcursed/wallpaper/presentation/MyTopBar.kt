package com.zxcursed.wallpaper.presentation

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zxcursed.wallpaper.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyTopBar(
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
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                contentDescription = "menu"
            )
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
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "back"
                )
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
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                    contentDescription = "more"
                )
            }
        }
    )
}