package com.chi.scopedstorage

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class DirectoryActivity : AppCompatActivity() {

    companion object {
        const val TAG = "chi->"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this

        Log.i(TAG, "externalCacheDir__________")
        val externalCacheDir = context.externalCacheDir
        Log.i(TAG, externalCacheDir?.absolutePath ?: "externalCacheDir null")

        val externalCacheDirs = context.externalCacheDirs
        Log.i(TAG, "externalCacheDirs__________")
        externalCacheDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$index: ${dir.absolutePath}")
            }
        }

        val externalMediaDirs = context.externalMediaDirs
        Log.i(TAG, "externalMediaDirs__________")
        externalMediaDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$index: ${dir.absolutePath}")
            }
        }

        Log.i(TAG, "getExternalFilesDir__________")
        val nullDir = getExternalFilesDir(null)
        Log.i(TAG, "nullDir: $nullDir")
        val musicDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        Log.i(TAG, "musicDir: $musicDir")
        val podcastsDir = context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS)
        Log.i(TAG, "podcastsDir: $podcastsDir")
        val ringtonesDir = context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)
        Log.i(TAG, "ringtonesDir: $ringtonesDir")
        val alarmsDir = context.getExternalFilesDir(Environment.DIRECTORY_ALARMS)
        Log.i(TAG, "alarmsDir: $alarmsDir")
        val notificationsDir = context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)
        Log.i(TAG, "notificationsDir: $notificationsDir")
        val picturesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        Log.i(TAG, "picturesDir: $picturesDir")
        val moviesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        Log.i(TAG, "moviesDir: $moviesDir")

        Log.i(TAG, "getExternalFilesDirs__________")
        var prefix = ""
        val nullDirs = context.getExternalFilesDirs(null)
        prefix = "nullDirs"
        nullDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$prefix $index: ${dir.absolutePath}")
            }
        }
        val musicDirs = context.getExternalFilesDirs(Environment.DIRECTORY_MUSIC)
        prefix = "musicDirs"
        musicDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$prefix $index: ${dir.absolutePath}")
            }
        }
        val podcastsDirs = context.getExternalFilesDirs(Environment.DIRECTORY_PODCASTS)
        prefix = "podcastsDirs"
        podcastsDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$prefix $index: ${dir.absolutePath}")
            }
        }
        val ringtonesDirs = context.getExternalFilesDirs(Environment.DIRECTORY_RINGTONES)
        prefix = "ringtonesDirs"
        ringtonesDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$prefix $index: ${dir.absolutePath}")
            }
        }
        val alarmsDirs = context.getExternalFilesDirs(Environment.DIRECTORY_ALARMS)
        prefix = "alarmsDirs"
        alarmsDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$prefix $index: ${dir.absolutePath}")
            }
        }
        val notificationsDirs = context.getExternalFilesDirs(Environment.DIRECTORY_NOTIFICATIONS)
        prefix = "notificationsDirs"
        notificationsDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$prefix $index: ${dir.absolutePath}")
            }
        }
        val picturesDirs = context.getExternalFilesDirs(Environment.DIRECTORY_PICTURES)
        prefix = "picturesDirs"
        picturesDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$prefix $index: ${dir.absolutePath}")
            }
        }
        val moviesDirs = context.getExternalFilesDirs(Environment.DIRECTORY_MOVIES)
        prefix = "moviesDirs"
        moviesDirs?.let {
            for ((index, dir) in it.withIndex()) {
                Log.i(TAG, "$prefix $index: ${dir.absolutePath}")
            }
        }
    }
}
