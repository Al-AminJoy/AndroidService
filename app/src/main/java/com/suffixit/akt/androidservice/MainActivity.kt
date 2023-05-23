package com.suffixit.akt.androidservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var btnStartService:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        btnStartService.setOnClickListener {
            for (song in songs()){
                val intent = Intent(this,MyDownloadService::class.java)
                intent.putExtra("SONG_NAME",song)
                startService(intent)
            }
        }
    }

    private fun initViews() {
        btnStartService = findViewById(R.id.btnRun)
    }

    fun songs():List<String>{
        return listOf("A Song","B Song","C Song","D Song")
    }
}