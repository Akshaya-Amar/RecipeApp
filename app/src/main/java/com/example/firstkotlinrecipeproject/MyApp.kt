package com.example.firstkotlinrecipeproject

import android.app.Application
import android.content.Context
import com.example.firstkotlinrecipeproject.util.MyDataStore
import java.lang.ref.WeakReference

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = WeakReference(this)
        dataStoreContext = MyDataStore(this)
    }

    companion object {
        private lateinit var context: WeakReference<Context>
        private lateinit var dataStoreContext: MyDataStore

        fun getContext(): WeakReference<Context> {
            return context
        }

        fun getDataStoreContext(): MyDataStore {
            return dataStoreContext
        }
    }
}