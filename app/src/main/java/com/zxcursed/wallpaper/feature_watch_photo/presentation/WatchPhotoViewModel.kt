package com.zxcursed.wallpaper.feature_watch_photo.presentation

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.zxcursed.wallpaper.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


class WatchPhotoViewModel : ViewModel() {
    private val _stateWidth = MutableStateFlow(0F)
    val stateWidth = _stateWidth.asStateFlow()

    private val _stateHeight = MutableStateFlow(0F)
    val stateHeight = _stateHeight.asStateFlow()

    fun triggerStates(height: Float, width: Float) {
        _stateHeight.value = height
        _stateWidth.value = width
    }

    fun copyToKeyBoard(context: Context, url: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("text", url))
        Toast.makeText(context, context.getString(R.string.url_coped), Toast.LENGTH_SHORT).show()
    }

    // SO MANY ISSUE WITH SET_WALLPAPER //
    @OptIn(DelicateCoroutinesApi::class)
    fun setImage(url: String, context: Context) {
        Toast.makeText(context, context.getText(R.string.wait_wallpaper), Toast.LENGTH_SHORT).show()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val inputStream = URL(url).openStream()
                WallpaperManager.getInstance(context).setStream(inputStream)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.established),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    fun download(url: String?, context: Context) {
        try {
            val request = DownloadManager.Request(Uri.parse(url))
            request.setDescription("Downloading")
            request.setMimeType("image/*")
            request.setTitle("File")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "photo.png"
            )
            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            manager!!.enqueue(request)
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    fun share(url: String?, context: Context) {
        try {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/*"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}
