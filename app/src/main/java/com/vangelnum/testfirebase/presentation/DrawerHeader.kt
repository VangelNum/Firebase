package com.vangelnum.testfirebase.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.vangelnum.testfirebase.MenuItems

@Composable
fun DrawerHeader(auth: FirebaseAuth) {

    val currentUser = auth.currentUser
    val photo = currentUser?.photoUrl
    val name = currentUser?.displayName
    val email = currentUser?.email

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
                SubcomposeAsyncImage(
                    model = photo,
                    contentDescription = "photo",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = name.toString())
            Text(text = email.toString())
        }
    }

}

@Composable
fun DrawerBody() {

    val items = listOf(
        MenuItems.Share,
        MenuItems.Stars,
        MenuItems.Contacts,
        MenuItems.SoundBoard,
        MenuItems.DrumPad
    )
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
    ) {
        itemsIndexed(items) { index, item ->
            Row(modifier = Modifier
                .clickable {

                }
            ) {
                if (index > 2) {
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
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = item.title,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "Developer",color = Color.Transparent, modifier = Modifier.clickable {

            })
        }

    }
}