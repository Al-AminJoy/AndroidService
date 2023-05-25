package com.suffixit.akt.androidservice

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
private const val TAG = "MyIntentService"

class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        setIntentRedelivery(true)
        val songName = intent!!.getStringExtra("SONG_NAME")
        Log.d(TAG, "downloadSong:  $songName ");
        downloadSong(songName!!)
    }

    private fun downloadSong(fileName: String) {
        Log.d(TAG, "downloadSong: Stared $fileName")

       try {
           Thread.sleep(4000)
       }catch (e : Exception){

       }
        val intent = Intent(SERVICE_MESSAGE)
        intent.putExtra("SONG_STATUS","$fileName Downloaded")
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

        Log.d(TAG, "downloadSong: Ended $fileName")

    }
}