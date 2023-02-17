package com.zxcursed.wallpaper.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zxcursed.wallpaper.domain.MenuItems
import com.zxcursed.wallpaper.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
fun DrawerBody(navController: NavController, scaffoldState: ScaffoldState) {

    val items = listOf(
        MenuItems.Share,
        MenuItems.Stars,
        MenuItems.Contacts,
        MenuItems.Exit,
        MenuItems.SoundBoard,
        MenuItems.DrumPad,
    )

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
                onEvent(item, navController, scaffoldState, context, scope)
            })
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = stringResource(id = R.string.develeper), color = Color.Transparent, modifier = Modifier.clickable {
                navController.navigate(Screens.DeveloperJoinScreen.route)
            })
        }

    }
}


fun onEvent(
    title: MenuItems,
    navController: NavController,
    scaffoldState: ScaffoldState,
    context: Context,
    scope: CoroutineScope
) {

    when (title) {
        is MenuItems.Exit -> {
            scope.launch {
                scaffoldState.drawerState.close()
            }
            navController.navigate(Screens.Login.route)
            Firebase.auth.signOut()
        }
        is MenuItems.Share -> {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, "Спасибо за то, что поделился приложением! ❤")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "ПРСОТО ЧТО ТО С ЧЕМ ТО ЭТО ЛУЧШЕЕ ЧТО Я ВИДЕЛ В СВОЕЙ ЖИЗНИ СТАвлю ЛАЙК Ю. НЕТ ТЫЩУ ЛАЙКОВ !!111?: https://play.google.com/store/apps/details?id=com.zxcursed.wallpaper"
                )
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(sendIntent, "Share..."))
        }
        is MenuItems.SoundBoard -> {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=com.zxcursedsoundboard.apk")
            context.startActivity(intent)
        }
        is MenuItems.DrumPad -> {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=com.vangelnum.drumpad")
            context.startActivity(intent)
        }
        is MenuItems.Stars -> {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.zxcursed.wallpaper")
                )
            )
        }
        is MenuItems.Contacts -> {
            scope.launch {
                scaffoldState.drawerState.close()
            }
            navController.navigate(Screens.Contact.route, navOptions {
                launchSingleTop = true
            })
        }
    }
}
