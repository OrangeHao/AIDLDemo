package com.example.aidlclient

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.aidlserver.ServerAidlInterface
import com.example.aidlserver.bean.Person
import com.example.aidlserver.callback.AidlCommandCallBack
import kotlinx.android.synthetic.main.activity_main.*

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

        registerCallBackBtn.setOnClickListener {
            //重复注册会false
            try {
//                mService?.registerCallBack(mListener)
                mService?.registerCallBack(object :AidlCommandCallBack.Stub(){
                    override fun onCommandCallBack(key: String?, cmdStr: String?) {
                        addMessage("client onCommandCallBack:$key")
                        Log.d("czh",cmdStr)
                    }
                })
            }catch (e:RemoteException){
                Log.d("czh","client register false")
                addMessage("client register callback false")
            }
        }
    }


    private val mListener:AidlCommandCallBack=object :AidlCommandCallBack.Stub(){
        override fun onCommandCallBack(key: String?, cmdStr: String?) {
            addMessage("client onCommandCallBack:$cmdStr")
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

        nestScrollView.post {
            nestScrollView.fullScroll(View.FOCUS_DOWN)
        }
    }
}
