package com.chi.scopedstorage

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_pictures.*

class PicturesActivity : AppCompatActivity() {

    companion object {
        const val TAG = "chi->"
        const val REQUEST_PERMISSION = 1
    }

    private val uriList = ArrayList<Uri>()
    lateinit var adapter: PicturesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pictures)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initRecyclerView()
        } else {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(
                this,
                permissions,
                REQUEST_PERMISSION
            )
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
                    initRecyclerView()
                } else {
                    finish()
                }
            } else {
                finish()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun initRecyclerView() {
        recyclerView.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                recyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                val columns = 3
                val imageSize = recyclerView.width / columns
                adapter = PicturesAdapter(
                    uriList,
                    this@PicturesActivity,
                    imageSize,
                    object : PicturesAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val uri = uriList[position]
                            Intent(this@PicturesActivity, PictureActivity::class.java).let {
                                it.putExtra("uri", uri)
                                startActivity(it)
                            }
                        }
                    })
                recyclerView.layoutManager = GridLayoutManager(this@PicturesActivity, columns)
                recyclerView.adapter = adapter
                loadPictures()
                return false
            }
        })
    }

    private fun loadPictures() {
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            "${MediaStore.MediaColumns.DATE_ADDED} desc"
        )
        cursor?.apply {
            var count = 0
            while (moveToNext()) {
                count++
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                val uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                uriList.add(uri)
                adapter.notifyItemInserted(uriList.size - 1)
                Log.i(TAG, "Image URI: $uri")
            }
            cursor.close()
        }
    }
}
