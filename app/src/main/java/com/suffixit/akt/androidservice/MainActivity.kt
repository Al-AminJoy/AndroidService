package com.suffixit.akt.androidservice

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var btnStartService:Button
    private lateinit var btnPlay:Button
    private lateinit var txtSongName:TextView
    private lateinit var stringBuilder: StringBuilder
    private lateinit var messageBroadcast: MessageBroadcast
    private lateinit var intentFilter: IntentFilter

    private lateinit var musicPlayerService:MusicPlayerService
    private var mBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        btnStartService.setOnClickListener {
            txtSongName.text = ""
            stringBuilder.clear()
            for (song in songs()){
                val intent = Intent(this,MyIntentService::class.java)
                intent.putExtra("SONG_NAME",song)
                startService(intent)
            }

        }

        messageBroadcast = MessageBroadcast()
        intentFilter = IntentFilter(MUSIC_COMPLETE_SERVICE)
        /*
                intentFilter.also {
                    registerReceiver(messageBroadcast,it)
                }*/

        btnPlay.setOnClickListener {
            if (mBound){
                if (musicPlayerService.isPlaying()){
                    btnPlay.text = "Play"
                    musicPlayerService.pause()
                }else{
                    btnPlay.text = "Pause"
                    musicPlayerService.play()
                }
            }
        }
    }



    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent) {
            Log.d(TAG, "onReceive: ${intent.getStringExtra("DONE")}")
            /*stringBuilder.append(intent.getStringExtra("SONG_STATUS")).append("\n")
            txtSongName.text = stringBuilder.toString()*/

            if (intent.getStringExtra("DONE").equals("done",ignoreCase = true)){
                btnPlay.text = "Play"
            }
        }
    }

    private fun initViews() {
        btnStartService = findViewById(R.id.btnRun)
        btnPlay = findViewById(R.id.btnPlay)
        txtSongName = findViewById(R.id.txtSongNames)
    }

    private fun songs():List<String>{
        return listOf("A Song","B Song","C Song","D Song")
    }

    private val mServiceConnection  = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            val myServiceBinder = service as MusicPlayerService.MyServiceBinder
            musicPlayerService = myServiceBinder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    override fun onStart() {
        super.onStart()
        /*stringBuilder = StringBuilder()

        */

        val intent = Intent(this,MusicPlayerService::class.java)
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE)

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter)
    }

    override fun onStop() {
        super.onStop()
        if (mBound){
          unbindService(mServiceConnection)
           mBound = false
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }
    override fun onDestroy() {
        super.onDestroy()
     //   LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }
}