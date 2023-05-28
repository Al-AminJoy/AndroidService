package com.suffixit.akt.androidservice

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.IBinder
import android.util.Log

private const val TAG = "MyDownloadJob"

class MyDownloadJob : JobService(){

    private var isJobCancelled = false
    private var isSuccess = false

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob: ")

        Thread{
            Log.d(TAG, "onStartJob: Started")
            for (i in 1..10){
                if (isJobCancelled){
                    return@Thread
                }
                Thread.sleep(1000)
                Log.d(TAG, "onStartJob: Downloading $i")
            }
            Log.d(TAG, "onStartJob: Ended")
           // isSuccess = true
            jobFinished(params,false)

        }.start()

    return true

    }

    override fun onStopJob(params: JobParameters?): Boolean {
        isJobCancelled = true
        return true
    }

}