package com.example.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServerService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("czh","server onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("czh","server onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }


    private val mBinder: ServerAidlInterface.Stub = object : ServerAidlInterface.Stub() {

        override fun registData(data: String?) {
            Log.d("czh", "received from client:$data")
        }

        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("czh","server onDestroy")
    }
}
