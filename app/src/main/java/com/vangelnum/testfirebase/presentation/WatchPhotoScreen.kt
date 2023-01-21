package com.vangelnum.testfirebase.presentation

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.testfirebase.BottomSheetData
import com.vangelnum.testfirebase.MainViewModel
import com.vangelnum.testfirebase.room.FavouritePhotosEntity


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WatchPhotoScreen(
    url: String?,
    scaffoldState: BottomSheetScaffoldState,
    viewModel: MainViewModel,
) {
    val items = listOf(
        BottomSheetData.Favourite,
        BottomSheetData.Share,
        BottomSheetData.Download,
    )

    val context = LocalContext.current

    BottomSheetScaffold(scaffoldState = scaffoldState, sheetContent = {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)) {
            Spacer(modifier = Modifier.weight(3f))
            Divider(modifier = Modifier
                .height(4.dp)
                .weight(1f))
            Spacer(modifier = Modifier.weight(3f))
        }
        items.forEachIndexed { index, current ->
            ListItem(
                text = { Text(text = current.name) },
                icon = {
                    Icon(painter = painterResource(id = current.icon),
                        contentDescription = "icon")
                },
                modifier = Modifier.clickable {
                    when (index) {
                        0 -> {
                            viewModel.addFavouritePhoto(FavouritePhotosEntity(url!!))
                        }
                        1 -> {
                            share(url, context = context)
                        }
                        2 -> {
                            download(url, context = context)
                        }
                    }
                }
            )
        }
    }, sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetPeekHeight = 36.dp,
        drawerElevation = 0.dp
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 46.dp, top = 10.dp),
            shape = RoundedCornerShape(15.dp),
        ) {
            SubcomposeAsyncImage(
                model = url,
                contentDescription = "photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }
            )
        }
    }


}

private fun download(url: String?, context: Context) {
    val request = DownloadManager.Request(Uri.parse(url))
    request.setDescription("Downloading")
    request.setMimeType("image/*")
    request.setTitle("File")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
        "photo.png")
    val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
    manager!!.enqueue(request)
}


private fun share(url: String?, context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/*"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}
