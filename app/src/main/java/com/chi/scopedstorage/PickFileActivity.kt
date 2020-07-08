package com.chi.scopedstorage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class PickFileActivity : AppCompatActivity() {

    companion object {
        const val TAG = "chi->"
        const val REQUEST_CODE_OPEN_DOCUMENT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_file)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_OPEN_DOCUMENT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val uri = data.data
                    uri?.let {
                        val fileName = getFileNameByUri(uri)
                        copyFileFromUri(uri, fileName)
                    }
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onClick(view: View) {
        pickFile()
    }

    private fun pickFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // 选择指定类型的文件
        // */* 选择所有类型的文件
        // image/* 选择图片类型的文件
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_OPEN_DOCUMENT)
    }

    private fun getFileNameByUri(uri: Uri): String {
        var fileName = System.currentTimeMillis().toString()
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.apply {
            if (moveToFirst()) {
                fileName = getString(getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
            }
            close()
        }
        return fileName
    }

    private fun copyFileFromUri(uri: Uri, fileName: String) {
        var outputStream: OutputStream? = null
        try {
            val inputStream = contentResolver.openInputStream(uri)
            inputStream?.use {
                val dir = externalCacheDir
                val file = File(dir, fileName)
                outputStream = FileOutputStream(file)
                val bytes = ByteArray(1024 * 8)
                var length = inputStream.read(bytes)
                while (length >= 0) {
                    outputStream?.apply {
                        write(bytes, 0, length)
                        flush()
                    }
                    length = inputStream.read(bytes)
                }
                Log.i(TAG, "复制完成")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }
    }
}
