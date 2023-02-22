package com.zxcursed.wallpaper.feature_add_photo.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.feature_add_photo.domain.AddPhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddPhotoRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val context: Context,
    private val fireStore: FirebaseFirestore,
    private val fireStorage: FirebaseStorage
) : AddPhotoRepository {
    override suspend fun addPhoto(textValue: String) {
        try {
            val uid = auth.currentUser?.uid ?: return
            val collection = fireStore.collection("users").document(uid)
            val email = auth.currentUser?.email ?: return
            val scoreDocument = collection.get().await()

            if (scoreDocument.exists()) {
                val score = scoreDocument.getLong("score") ?: return
                val newScore = score + 1
                val mapUpdate = mapOf(
                    "url" to FieldValue.arrayUnion(textValue),
                    "score" to newScore,
                    "notification" to FieldValue.arrayUnion(
                        hashMapOf(
                            "name1" to textValue,
                            "name2" to "gray"
                        )
                    )
                )
                if (newScore < 11) {
                    collection.update(mapUpdate)
                    sendNotify(context, textValue, newScore)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Отправлено $newScore / 10",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Достигнуто ограничение",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                val mapUpdate =     mapOf(
                    "url" to FieldValue.arrayUnion(textValue),
                    "score" to 1L,
                    "notification" to FieldValue.arrayUnion(
                        hashMapOf(
                            "name1" to textValue,
                            "name2" to "gray"
                        )
                    ),
                    "email" to email,
                    "userId" to uid,
                )
                collection.set(mapUpdate).await().also {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Отправлено ${mapUpdate["score"]} / 10",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    sendNotify(context, textValue, 1L)
                }
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

    override suspend fun addPhotoToFirestorage(selectedPicUri: Uri?) {
        try {
            val uid = auth.currentUser?.uid ?: throw Exception("User ID is null")
            val collection = fireStore.collection("users").document(uid)
            val email = auth.currentUser?.email ?: throw Exception("User email is null")
            val scoreDocument = collection.get().await()

            if (scoreDocument.exists()) {
                val score = scoreDocument.getLong("score") ?: throw Exception("Score is null")
                val newScore = score + 1

                if (newScore < 11) {
                    loadToFireStore(
                        selectedPicUri,
                        context,
                        newScore,
                        collection,
                        email,
                        fireStorage
                    )
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Достигнуто ограничение",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                loadToFireStoreStarted(selectedPicUri, context, collection, email, uid, fireStorage)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Error: ${e.message.toString()}",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("tag", e.message.toString())
            }
        }
    }


}


private fun loadToFireStoreStarted(
    selectedPicUri: Uri?,
    context: Context,
    collection: DocumentReference,
    email: String,
    uid: String,
    fireStorage: FirebaseStorage,
) {
    val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
    val now = Date()
    val fileName = formatter.format(now)
    val storageReference: StorageReference = fireStorage.getReference("images/$email/$fileName")
    if (selectedPicUri != null) {
        Toast.makeText(context,context.getString(R.string.photo_upload),Toast.LENGTH_SHORT).show()
        storageReference.putFile(selectedPicUri).addOnSuccessListener { taskSnapShot ->
            taskSnapShot.metadata?.reference?.downloadUrl?.addOnSuccessListener { urlToPhoto ->
                val mapUpdate = mapOf(
                    "url" to FieldValue.arrayUnion(urlToPhoto.toString()),
                    "score" to 1L,
                    "notification" to FieldValue.arrayUnion(
                        hashMapOf(
                            "name1" to urlToPhoto.toString(),
                            "name2" to "gray"
                        )
                    ),
                    "email" to email,
                    "userId" to uid,
                )
                collection.set(mapUpdate).also {
                    Toast.makeText(
                        context,
                        "Отправлено ${mapUpdate["score"]} / 10",
                        Toast.LENGTH_LONG
                    ).show()
                    sendNotify(
                        context = context,
                        score = mapUpdate["score"] as Long,
                        textValue = urlToPhoto.toString()
                    )

                }
            }
        }.addOnFailureListener {
            sendNotifyForError(it.message.toString(), context = context)
        }
    }


}


private fun loadToFireStore(
    selectedPicUri: Uri?,
    context: Context,
    newScore: Long,
    collection: DocumentReference,
    email: String,
    fireStorage: FirebaseStorage
) {
    val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
    val now = Date()
    val fileName = formatter.format(now)
    val storageReference: StorageReference = fireStorage.getReference("images/$email/$fileName")
    if (selectedPicUri != null) {
        Toast.makeText(context,context.getString(R.string.photo_upload),Toast.LENGTH_SHORT).show()
        storageReference.putFile(selectedPicUri).addOnSuccessListener { taskSnapShot ->
            taskSnapShot.metadata?.reference?.downloadUrl?.addOnSuccessListener { urlToPhoto ->
                val mapUpdate = mapOf(
                    "url" to FieldValue.arrayUnion(urlToPhoto.toString()),
                    "score" to newScore,
                    "notification" to FieldValue.arrayUnion(
                        hashMapOf(
                            "name1" to urlToPhoto.toString(),
                            "name2" to "gray"
                        )
                    )
                )
                collection.update(mapUpdate)
                sendNotify(context = context, score = newScore, textValue = urlToPhoto.toString())
                Toast.makeText(
                    context,
                    "Отправлено $newScore / 10",
                    Toast.LENGTH_LONG
                ).show()
            }
        }.addOnFailureListener {
            sendNotifyForError(it.message.toString(), context = context)
        }
    }


}

private fun sendNotify(context: Context, textValue: String, score: Long) {
    val notificationId = 1
    val channelId = "channel-01"
    val channelName = "Channel Name"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
        .setContentTitle("Отправлено $score/10")
        .setContentText(textValue)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
    notificationManager.notify(notificationId, builder.build())
}

private fun sendNotifyForError(errorMessage: String, context: Context) {
    val notificationId = 1
    val channelId = "channel-02"
    val channelName = "Channel Name2"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
        .setContentTitle("Ошибка отправления")
        .setContentText(errorMessage)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
    notificationManager.notify(notificationId, builder.build())
}
