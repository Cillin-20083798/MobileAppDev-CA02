package org.wit.dpscalculatorapp.main

import DDJSONStore
import DOTJSONStore
import android.app.Application

class MainApp : Application() {

    lateinit var dots: DOTJSONStore
    lateinit var dds: DDJSONStore

    override fun onCreate() {
        super.onCreate()
        dots = DOTJSONStore(applicationContext)
        dds = DDJSONStore(applicationContext)
    }
}