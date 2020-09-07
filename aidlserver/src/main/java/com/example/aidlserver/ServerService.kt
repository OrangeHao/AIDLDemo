package com.example.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.util.Log
import com.example.aidlserver.bean.Person
import com.example.aidlserver.callback.AidlCommandCallBack

class ServerService : Service() {



    private var mCallBacks=RemoteCallbackList<AidlCommandCallBack>()



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
            callClientTest()
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


        override fun registerCallBack(listener: AidlCommandCallBack?) {
            Log.d("czh","server registerCallBack")
            mCallBacks.register(listener)
            callClientTest()
        }

        override fun removeCallBack(listener: AidlCommandCallBack?) {
            Log.d("czh","server registerCallBack")
            mCallBacks.unregister(listener)
        }

        override fun customDataTest(persion: Person?) {
            Log.d("czh","server: received customData from client:${persion?.name}")
        }

    }


    /**
     * callback的数量增长会导致广播的时间剧增！！
     */
    fun callClientTest(){
        val timeA=System.currentTimeMillis()
        val size=mCallBacks.beginBroadcast()
        for (index in 0 until size){
            try {
                Log.d("czh","server onCommandCallBack $index")
                val temp=mCallBacks.getBroadcastItem(index)
                temp.onCommandCallBack("$index",testStr)
            }catch (e:RemoteException){

            }
        }
        mCallBacks.finishBroadcast()
        val timeB=System.currentTimeMillis()
        Log.d("czh","server Broadcast Time:${(timeB-timeA)/1000f}")
    }

    val testStr="坷拉激发第哦啊就是饭卡上考虑的封建科举啊圣诞节分厘卡" +
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"+
            "监考老师酒店开房间看似简单积分了解的了解绿卡接近疯狂撒低级覅偶尔解放了奖励" +
            "积分累计丽莎阿胶欧杰瓦欸放假啊尽快发教案课件覅哦减肥挖掘飞机上发动机哦i我"
    override fun onDestroy() {
        super.onDestroy()
        Log.d("czh","server onDestroy")
    }
}
