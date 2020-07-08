package com.chi.scopedstorage

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_picture.*
import kotlin.concurrent.thread

class PictureActivity : AppCompatActivity() {

    lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)
        uri = intent.getParcelableExtra("uri") as Uri
        loadPicture()
    }

    private fun loadPicture() {
        thread {
            val bitmap = getBitmapFromUri(contentResolver, uri)
            runOnUiThread {
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    private fun getBitmapFromUri(contentResolver: ContentResolver, uri: Uri): Bitmap? {
        var bitmap: Bitmap? = null
        contentResolver.openFileDescriptor(uri, "r")?.apply {
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            close()
        }
        return bitmap
    }

    fun onClick(view: View) {}
}
