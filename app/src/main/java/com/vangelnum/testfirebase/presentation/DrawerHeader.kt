package com.vangelnum.testfirebase.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vangelnum.testfirebase.MenuItems
import com.vangelnum.testfirebase.R
import com.vangelnum.testfirebase.Screens

@Composable
fun DrawerHeader(auth: FirebaseAuth) {

    val currentUser = auth.currentUser
    val photo = currentUser?.photoUrl
    val name = currentUser?.displayName.toString()
    val email = currentUser?.email.toString()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Card(
                shape = CircleShape,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
            ) {
                if (photo != null) {
                    SubcomposeAsyncImage(
                        model = photo,
                        contentDescription = "photo",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_person_24),
                        contentDescription = "person",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (name != "null") {
                Text(text = name)
            }
            if (email != "null") {
                Text(text = email)
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerBody(navController: NavController) {

    val items = listOf(
        MenuItems.Share,
        MenuItems.Stars,
        MenuItems.Contacts,
        MenuItems.Exit,
        MenuItems.SoundBoard,
        MenuItems.DrumPad,
    )

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        itemsIndexed(items) { index, item ->
            ListItem(text = {
                Text(text = item.title)
            }, icon = {
                if (index > 3) {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }, modifier = Modifier.clickable {
                onEvent(item, navController)
            })
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "Developer", color = Color.Transparent, modifier = Modifier.clickable {

            })
        }

    }
}


fun onEvent(title: MenuItems, navController: NavController) {
    when (title) {
        is MenuItems.Exit -> {
            Firebase.auth.signOut()
            navController.navigate(Screens.Login.route)
        }
        else -> {

        }
    }
}
