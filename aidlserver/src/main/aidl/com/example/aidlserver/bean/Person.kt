package com.example.aidlserver.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * created by czh on 2020/8/28
 */
data class Person(val name: String = "", val age: Int = 20) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeInt(age)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Person> = object : Parcelable.Creator<Person> {
            override fun createFromParcel(source: Parcel): Person = Person(source)
            override fun newArray(size: Int): Array<Person?> = arrayOfNulls(size)
        }
    }
}