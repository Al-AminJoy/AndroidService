package com.suffixit.akt.androidservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager


public const val SERVICE_MESSAGE = "ServiceMessage"
private const val TAG = "MyDownloadService"

class MyDownloadService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val songName = intent.getStringExtra("SONG_NAME")
        Log.d(TAG, "downloadSong: onStartCommand $songName $startId")

        downloadSong(songName!!, startId)
        return START_REDELIVER_INTENT
    }

    private fun downloadSong(fileName: String, startId: Int) {
        Log.d(TAG, "downloadSong: Stared $fileName")

        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            stopSelf(startId)

            val intent = Intent(SERVICE_MESSAGE)
            intent.putExtra("SONG_STATUS","$fileName Downloaded")
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

            Log.d(TAG, "downloadSong: Ended $startId")
        }
        handler.postDelayed(runnable, 4000L)





    }
}