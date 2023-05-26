package com.suffixit.akt.androidservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MusicPlayerService: Service() {

    private val mBinder = MyServiceBinder()

    override fun onCreate() {
        super.onCreate()
    }

    inner class MyServiceBinder:Binder(){
        fun getService():MusicPlayerService  {
            return this@MusicPlayerService
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun getValue():String{
        return "Data From Service"
    }

}