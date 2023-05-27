package com.suffixit.akt.androidservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager

const val MUSIC_COMPLETE_SERVICE = "MUSIC_COMPLETE_SERVICE"
class MusicPlayerService: Service() {

    private val mBinder = MyServiceBinder()
    private lateinit var mediaPlayer:MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this,R.raw.romantic)

        mediaPlayer.setOnCompletionListener {
            val intent = Intent(MUSIC_COMPLETE_SERVICE)
            intent.putExtra("DONE","done")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            stopSelf()
        }
    }

    inner class MyServiceBinder:Binder(){
        fun getService():MusicPlayerService  {
            return this@MusicPlayerService
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()

    }

    fun isPlaying():Boolean{
      return  mediaPlayer.isPlaying
    }

    fun  play(){
        mediaPlayer.start()
    }

    fun pause(){
        mediaPlayer.pause()
    }


}