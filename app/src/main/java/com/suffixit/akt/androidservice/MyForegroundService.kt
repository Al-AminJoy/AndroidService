package com.suffixit.akt.androidservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

private const val TAG = "MyForegroundService"
class MyForegroundService : Service() {


    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not Yet Implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        Thread {
            Log.d(TAG, "onStartCommand: Download Started")
            for (i in 1..10){
                Thread.sleep(1000)
                Log.d(TAG, "onStartCommand: Downloading $i")
            }
            Log.d(TAG, "onStartCommand: Download Complete")
            stopForeground(true)
            stopSelf()
        }.start()

        return START_NOT_STICKY
    }

    private fun showNotification() {
        val builder = NotificationCompat.Builder(this,"channelId")
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("This is Service Notification")
            .setContentTitle("Title")

        val notification = builder.build()

        startForeground(123,notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}