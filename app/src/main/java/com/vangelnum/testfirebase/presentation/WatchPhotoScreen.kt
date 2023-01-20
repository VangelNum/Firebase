package com.vangelnum.testfirebase.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.vangelnum.testfirebase.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WatchPhotoScreen(url: String?, navController: NavController) {
    BottomSheetScaffold(sheetContent = {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row() {
                    Icon(painter = painterResource(R.drawable.ic_baseline_download_24), contentDescription = "download")
                    Text(text = "Скачать", fontSize = 20.sp)
                }
                Row() {
                    Icon(painter = painterResource(R.drawable.ic_baseline_share_24), contentDescription = "download")
                    Text(text = "Поделиться", fontSize = 20.sp)
                }
                Row() {
                    Icon(painter = painterResource(R.drawable.ic_round_favorite_24), contentDescription = "download")
                    Text(text = "Добавить в избранное", fontSize = 20.sp)
                }
            }
        }
    }) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            AsyncImage(
                model = url,
                contentDescription = "photo",
                contentScale = ContentScale.Crop
            )
        }
    }

    
}