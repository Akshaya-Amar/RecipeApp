package com.example.firstkotlinrecipeproject

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        context = WeakReference(this)
    }

    companion object {
        private lateinit var context: WeakReference<Context>
        fun getContext(): WeakReference<Context> {
            return context
        }
    }
}