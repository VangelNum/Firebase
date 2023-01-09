package com.vangelnum.firebase

import android.app.Application
import android.content.Context

open class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object {
        lateinit var mInstance: MyApp
        fun getContext(): Context? {
            return mInstance.applicationContext
        }
    }
}