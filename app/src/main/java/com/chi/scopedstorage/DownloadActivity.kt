package com.chi.scopedstorage

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class DownloadActivity : AppCompatActivity() {

    companion object {
        const val TAG = "chi->"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
    }

    fun onClick(view: View) {
        val downloadUrl = "http://192.168.1.104:8080/chi/fgo/xx.gif"
        val fileName = "xx.gif"
        downloadFile(downloadUrl, fileName)
    }

    private fun downloadFile(downloadUrl: String, fileName: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return
        }

        thread {
            var inputStream: InputStream? = null
            try {
                val url = URL(downloadUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 3000
                connection.readTimeout = 3000
                val inputStream = connection.inputStream
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DOWNLOADS
                )
                val uri = contentResolver.insert(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    contentValues
                )
                uri?.let {
                    val outputStream = contentResolver.openOutputStream(uri)
                    outputStream?.use {
                        val bytes = ByteArray(1024 * 8)
                        var length = inputStream.read(bytes)
                        while (length >= 0) {
                            outputStream.write(bytes, 0, length)
                            outputStream.flush()
                            length = inputStream.read(bytes)
                        }
                        Log.i(TAG, "下载完成")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                inputStream?.apply { close() }
            }
        }
    }
}
