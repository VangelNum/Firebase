package com.vangelnum.firebase

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vangelnum.firebase.ui.theme.FirebaseTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseTheme {
               Greeting(this)
            }
        }
    }
}


@Composable
fun Greeting(context: ComponentActivity) {
    val auth = Firebase.auth

    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        val emailValue = remember {
            mutableStateOf(TextFieldValue())
        }
        val passwordValue = remember {
            mutableStateOf(TextFieldValue())
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(value = emailValue.value, onValueChange = {
                emailValue.value = it }
            )
            OutlinedTextField(value = passwordValue.value, onValueChange = {
                passwordValue.value = it }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {
                auth.createUserWithEmailAndPassword(
                    emailValue.value.text.trim(),
                    passwordValue.value.text.trim()
                ).addOnCompleteListener(context) { task->
                    if(task.isSuccessful) {
                        Log.d("TAG","succes")
                    } else {
                        Log.d("TAG","Fail ${task.exception}")
                    }
                }
            }) {
                Text(text = "Register")
            }

        }
    }
}