package com.example.aidlclient

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.example.aidlserver.ServerAidlInterface
import com.example.aidlserver.bean.Person
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val mSb:StringBuilder= StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
    }


    private fun initListener(){
        connectBtn.setOnClickListener {
            connectServer()
        }

        sendBtn.setOnClickListener {
            addMessage("send message to server:")
            mService?.registData("1234")
            mService?.customDataTest(Person("client person"))
        }

        clearBtn.setOnClickListener {
            mSb.clear()
            messageShowText.text = ""
        }
    }

    private fun connectServer(){
        if (mService==null){
            val intent=Intent(ServerAidlInterface::class.java.name)
            intent.action = "com.example.aidlserver.service"
            intent.setPackage("com.example.aidlserver")
            bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }


    private var mService:ServerAidlInterface?= null

    private val serviceConnection:ServiceConnection=object :ServiceConnection{

        override fun onServiceDisconnected(componentName: ComponentName?) {
            Log.d("czh","onServiceDisconnected")
            addMessage("onServiceDisconnected")
            mService=null
        }

        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            addMessage("onServiceConnected")

            try {
                iBinder?.linkToDeath(mDeathRecipient,0)
            }catch (e:RemoteException){
                Log.d("czh","RemoteException:$e")
            }

            mService=ServerAidlInterface.Stub.asInterface(iBinder)
        }

    }


    //死亡代理(onServiceDisconnected在客户端的UI线程中被回调，而binderDied在客户端的Binder线程池中被回调
    private  var mDeathRecipient:IBinder.DeathRecipient=object :IBinder.DeathRecipient {
        override fun binderDied() {
            Log.d("czh","binderDied")
            //解绑
            if (mService!=null){
                mService!!.asBinder().unlinkToDeath(this,0)
                mService=null
            }

            //重新连接
//            connectServer()
        }
    }

    private fun addMessage(message:String){
        mSb.append(message).append('\n')
        messageShowText.text = mSb.toString()
    }
}
