package com.suffixit.akt.androidservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

private const val TAG = "MessageBroadcast"
class MessageBroadcast: BroadcastReceiver() {
    var stringBuilder: StringBuilder = StringBuilder()

    override fun onReceive(context: Context, intent: Intent) {

        Log.d(TAG, "onReceive: ${intent.getStringExtra("SONG_STATUS")}")
       // stringBuilder.append(intent.getStringExtra("SONG_STATUS"))
        //context.txtSongName.text = stringBuilder.toString()
    }
}