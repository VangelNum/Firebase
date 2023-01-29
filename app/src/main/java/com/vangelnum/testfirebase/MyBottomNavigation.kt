package com.vangelnum.testfirebase

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination


@Composable
fun MyBottomNavigation(
    items: List<Screens>,
    currentDestination: NavDestination?,
    navController: NavController,
) {
    BottomNavigation(elevation = 0.dp) {
        items.forEach { screen ->
            if (currentDestination != null) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(
                            text = screen.title,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    },
                    selected = currentDestination.hierarchy.any {
                        it.route == screen.route
                    },
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
