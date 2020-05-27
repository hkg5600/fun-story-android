package com.example.fun_story

import android.app.Application
import com.example.fun_story.di.moduleList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(moduleList)
        }
    }
}