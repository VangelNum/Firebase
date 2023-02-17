package com.zxcursed.wallpaper.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.feature_watch_photo.presentation.WatchPhotoViewModel
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
    sheetState: BottomSheetState,
    viewModelWatchPhoto: WatchPhotoViewModel
) {
    val scope = rememberCoroutineScope()
    val width = viewModelWatchPhoto.stateWidth.collectAsState()
    val height = viewModelWatchPhoto.stateHeight.collectAsState()
    TopAppBar(
        elevation = 0.dp,
        title = {
            Text(
                text = width.value.toInt().toString() + " x " + height.value.toInt().toString(),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.atypdisplaynew)),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
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