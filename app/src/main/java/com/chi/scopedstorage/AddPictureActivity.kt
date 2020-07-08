package com.chi.scopedstorage

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.InputStream

class AddPictureActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_picture)
    }

    fun onClick(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            addPicture()
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                addPicture()
            } else {
                val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    REQUEST_PERMISSION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION -> if (grantResults.isNotEmpty()) {
                var granted = true
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        granted = false
                        break
                    }
                }
                if (granted) {
                    addPicture()
                } else {
                    finish()
                }
            } else {
                finish()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun addPicture() {
        addPictureFromStream()
    }

    private fun addPictureFromBitmap() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.dog)
        val displayName = "${System.currentTimeMillis()}.jpg"
        val mimeType = "image/jpeg"
        val compressFormat = Bitmap.CompressFormat.JPEG
        insertAlbumFromBitmap(bitmap, displayName, mimeType, compressFormat)
    }

    private fun insertAlbumFromBitmap(
        bitmap: Bitmap,
        displayName: String,
        mimeType: String,
        compressFormat: Bitmap.CompressFormat
    ) {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 新增了 MediaStore.MediaColumns.RELATIVE_PATH, 表示文件的相对路径
            // Environment.DIRECTORY_DCIM: 相册
            // Environment.DIRECTORY_PICTURES: 图片
            // ...
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        } else {
            // Android 10 之前使用绝对路径
            // 需要 Manifest.permission.WRITE_EXTERNAL_STORAGE 权限
            contentValues.put(
                MediaStore.MediaColumns.DATA,
                "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_DCIM}/$displayName"
            )
        }
        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            val outputStream = contentResolver.openOutputStream(uri)
            outputStream?.use {
                bitmap.compress(compressFormat, 100, outputStream)
            }
        }
    }

    private fun addPictureFromStream() {
        val inputStream: InputStream = assets.open("dog2.jpg")
        val displayName = "${System.currentTimeMillis()}.jpg"
        val mimeType = "image/jpeg"
        insertAlbumFromStream(inputStream, displayName, mimeType)
    }

    private fun insertAlbumFromStream(
        inputStream: InputStream,
        displayName: String,
        mimeType: String
    ) {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 新增了 MediaStore.MediaColumns.RELATIVE_PATH, 表示文件的相对路径
            // Environment.DIRECTORY_DCIM: 相册
            // Environment.DIRECTORY_PICTURES: 图片
            // ...
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        } else {
            // Android 10 之前使用绝对路径
            // 需要 Manifest.permission.WRITE_EXTERNAL_STORAGE 权限
            contentValues.put(
                MediaStore.MediaColumns.DATA,
                "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_DCIM}/$displayName"
            )
        }
        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
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
            }
        }
        inputStream.close()
    }
}
