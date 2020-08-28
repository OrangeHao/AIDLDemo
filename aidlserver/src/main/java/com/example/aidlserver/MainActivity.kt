package com.example.aidlserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aidlserver.bean.Person
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val mSb: StringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
    }


    private fun initListener(){
        connectBtn.setOnClickListener {

        }

        sendBtn.setOnClickListener {
            addMessage("send message to client:")
        }

        clearBtn.setOnClickListener {
            mSb.clear()
            messageShowText.text = ""
        }
    }


    private fun addMessage(message:String){
        mSb.append(message).append('\n')
        messageShowText.text = mSb.toString()
    }
}
