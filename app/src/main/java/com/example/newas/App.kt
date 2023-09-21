package com.example.newas

import android.app.Application
import android.content.Context


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}

private lateinit var application: Context

fun context(): Context {
    return application
}