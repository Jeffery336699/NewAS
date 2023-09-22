package com.example.newas.ext

import android.util.Log
import android.widget.Toast
import com.example.newas.context

fun Any?.toast(duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context(), this.toString(), duration).apply { show() }
}

fun Any?.log(tag: String = "System.out") {
    Log.i(tag, this.toString())
}

