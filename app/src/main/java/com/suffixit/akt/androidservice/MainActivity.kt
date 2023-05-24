package com.suffixit.akt.androidservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var btnStartService:Button
    private lateinit var txtSongName:TextView
    private lateinit var stringBuilder: StringBuilder
    private lateinit var messageBroadcast: MessageBroadcast
    private lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        btnStartService.setOnClickListener {
            txtSongName.text = ""
            stringBuilder.clear()
            for (song in songs()){
                val intent = Intent(this,MyDownloadService::class.java)
                intent.putExtra("SONG_NAME",song)
                startService(intent)
            }
        }

        messageBroadcast = MessageBroadcast()
        intentFilter = IntentFilter(SERVICE_MESSAGE)
        /*
                intentFilter.also {
                    registerReceiver(messageBroadcast,it)
                }*/
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent) {
            Log.d(TAG, "onReceive: ${intent.getStringExtra("SONG_STATUS")}")
            stringBuilder.append(intent.getStringExtra("SONG_STATUS")).append("\n")
            txtSongName.text = stringBuilder.toString()
        }
    }

    private fun initViews() {
        btnStartService = findViewById(R.id.btnRun)
        txtSongName = findViewById(R.id.txtSongNames)
    }

    private fun songs():List<String>{
        return listOf("A Song","B Song","C Song","D Song")
    }

    override fun onStart() {
        super.onStart()
        stringBuilder = StringBuilder()
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }
}