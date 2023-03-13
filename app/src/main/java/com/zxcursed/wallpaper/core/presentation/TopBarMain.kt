package com.zxcursed.wallpaper.core.presentation

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zxcursed.wallpaper.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainTopBar(
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


