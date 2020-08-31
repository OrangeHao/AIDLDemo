// ServerAidlInterface.aidl
package com.example.aidlserver;

import com.example.aidlserver.bean.Person;
import com.example.aidlserver.callback.AidlCommandCallBack;
// Declare any non-default types here with import statements

interface ServerAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void registData(String data);

    void customDataTest(in Person persion);

     void registerCallBack(AidlCommandCallBack listener);
    void removeCallBack(AidlCommandCallBack listener);
}
