package com.suffixit.akt.androidservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

private const val TAG = "MyDownloadService"
class MyDownloadService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val songName = intent.getStringExtra("SONG_NAME")
        downloadSong(songName!!)
        return START_REDELIVER_INTENT
    }
    
    private fun downloadSong(fileName:String) {
        Log.d(TAG, "downloadSong: Stared $fileName")
        try {
            Thread.sleep(4000)
        }catch (e:Exception){

        }
        Log.d(TAG, "downloadSong: Ended")

    }
}