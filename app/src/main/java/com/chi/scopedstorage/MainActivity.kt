package com.chi.scopedstorage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button1 -> {
                startActivity(Intent(this, DirectoryActivity::class.java))
            }
            R.id.button2 -> {
                startActivity(Intent(this, PicturesActivity::class.java))
            }
            R.id.button3 -> {
                startActivity(Intent(this, AddPictureActivity::class.java))
            }
            R.id.button4 -> {
                startActivity(Intent(this, DownloadActivity::class.java))
            }
            R.id.button5 -> {
                startActivity(Intent(this, PickFileActivity::class.java))
            }
        }
    }
}
