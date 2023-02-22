package com.zxcursed.wallpaper.feature_add_photo.presentation

import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.zxcursed.wallpaper.R


@Composable
fun AddPhotoFromGallery() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        LimitationHeader()
        GalleryScreenBody()
    }
}

@Composable
fun GalleryScreenBody(viewModel: AddPhotoViewModel = hiltViewModel()) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->


        val cursor: Cursor? = uri?.let {
            context.contentResolver?.query(
                it, null, null, null, null
            )
        }

        cursor?.use {
            val sizeColumn = it.getColumnIndex(OpenableColumns.SIZE)
            if (it.moveToNext()) {
                val sizeInMb = it.getDouble(sizeColumn) / (1024 * 1024)
                if (sizeInMb <= 3f) {
                    imageUri = uri
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.more_than_3mb),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    OutlinedButton(
        onClick = {
            try {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }, colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier.height(60.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Text(
            text = stringResource(id = R.string.choose_from_galery),
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
            )
        )
    }
    imageUri?.let {
        OutlinedButton(
            onClick = {
                viewModel.addPhotoToFireStorage(it)
            }, colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier.height(60.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                text = stringResource(id = R.string.send),
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                )
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            SubcomposeAsyncImage(
                model = it,
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                },
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}


