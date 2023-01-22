package com.vangelnum.testfirebase.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vangelnum.testfirebase.R
import com.vangelnum.testfirebase.feature_developer.domain.model.UserPhotos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun AddPhotoScreen(auth: FirebaseAuth) {

    val textValue = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 10.dp, end = 10.dp)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Здесь вы можете предложить свои варианты фотографий курседа.",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(text = "Ограничения:", color = Color.Red, style = MaterialTheme.typography.body2)
        Text(text = "1. Максимум 10 фотографий, ограничение связано с бесплатностью приложения.")
        Text(text = "2. Нельзя предлагать нацизм, фашизм, нацистскую символику и так далее.")
        Text(text = "За нарушение второго пункта ваш аккаунт может быть заблокирован!")

        OutlinedTextField(value = textValue.value, onValueChange = {
            textValue.value = it
        }, modifier = Modifier.fillMaxWidth(), label = {
            Text(text = "Фото курседа URL")
        }, maxLines = 1, shape = RoundedCornerShape(15.dp), singleLine = true, trailingIcon = {
            IconButton(onClick = { textValue.value = "" }) {
                Icon(painter = painterResource(id = R.drawable.ic_round_close_24),
                    contentDescription = "delete field")
            }
        })
        OutlinedButton(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val uid = auth.currentUser?.uid.toString()
                    val collection = Firebase.firestore.collection("users").document(uid)
                    val score = collection.get().await().get("score")
                    if (score != null) {
                        val newScore: Long = score as Long + 1
                        val mapUpdate = mapOf(
                            "url" to FieldValue.arrayUnion(textValue.value),
                            "score" to newScore
                        )
                        if (newScore < 11) {
                            collection.update(mapUpdate)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context,
                                    "Отправлено $newScore / 10",
                                    Toast.LENGTH_LONG).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Достигнуто ограничение", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    } else {
                        val email = auth.currentUser?.email!!
                        val person = UserPhotos(email = email, userId = uid, score = 0)
                        collection.set(person).await()
                        val mapUpdate = mapOf(
                            "url" to FieldValue.arrayUnion(textValue.value),
                            "score" to 1.toLong()
                        )
                        collection.update(mapUpdate).await().also {

                            withContext(Dispatchers.Main) {
                                Toast.makeText(context,
                                    "Отправлено ${mapUpdate["score"]} / 10",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error: ${e.message.toString()}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text = "Отправить")
        }

        if (textValue.value.isEmpty()) {
            Text(
                text = "Вставьте ссылку и курсед появится, если этого не произошло, вероятно, ссылка не работает",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(bottom = 10.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                SubcomposeAsyncImage(
                    model = textValue.value,
                    loading = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator()
                        }
                    },
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}