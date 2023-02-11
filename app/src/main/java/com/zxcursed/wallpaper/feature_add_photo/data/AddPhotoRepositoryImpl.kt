package com.zxcursed.wallpaper.feature_add_photo.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.feature_add_photo.domain.AddPhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPhotoRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val context: Context
) : AddPhotoRepository {
    override suspend fun addPhoto(textValue: String) {
        try {
            val uid = auth.currentUser?.uid.toString()
            val collection = Firebase.firestore.collection("users").document(uid)
            val email = auth.currentUser?.email!!
            val score = collection.get().await().get("score")

            if (score != null) {
                val newScore: Long = score as Long + 1
                val mapUpdate = mapOf(
                    "url" to FieldValue.arrayUnion(textValue),
                    "score" to newScore,
                    "notification" to FieldValue.arrayUnion(
                        mapOf(
                            "name1" to textValue,
                            "name2" to "gray"
                        )
                    )
                )
                if (newScore < 11) {
                    collection.update(mapUpdate)
                    sendNotify(context)
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
                val mapUpdate = mapOf(
                    "url" to FieldValue.arrayUnion(textValue),
                    "score" to 1L,
                    "notification" to FieldValue.arrayUnion(
                        mapOf(
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
                    sendNotify(context)
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
}

private fun sendNotify(context: Context) {
    val notificationId = 1
    val channelId = "channel-01"
    val channelName = "Channel Name"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
        .setContentTitle("Отправлено")
        .setContentText("Мегахарош")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notificationManager.notify(notificationId, builder.build())
}