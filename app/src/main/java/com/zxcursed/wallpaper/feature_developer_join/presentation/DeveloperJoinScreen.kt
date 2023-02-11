package com.zxcursed.wallpaper.feature_developer_join.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zxcursed.wallpaper.presentation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun DeveloperJoinScreen(uid: String?, navController: NavController) {

    var valueText by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        OutlinedTextField(value = valueText, onValueChange = {
            valueText = it
        }, keyboardActions = KeyboardActions(onDone = {

            if (valueText == "10151015") {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val myCollection = Firebase.firestore.collection("developer").document(uid!!)
                        val querySnapShot = myCollection.get().await()
                        withContext(Dispatchers.Main) {
                            navController.navigate(route = Screens.Developer.route)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Error: ${e.message.toString()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done))
    }
}