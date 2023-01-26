package com.vangelnum.testfirebase.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = email.toString())
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Composable
fun DrawerBody() {

    val items = listOf(
        MenuItems.Share,
        MenuItems.Stars,
        MenuItems.Contacts
    )
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
    ) {
        items(items) { item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {

                }
            ) {
                Icon(painter = painterResource(id = item.icon), contentDescription = item.title)
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = item.title,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}