package com.dynasty.dynastyscansapp

import android.app.Application
import com.dynasty.dynastyscansapp.di.appModule
import com.dynasty.dynastyscansapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    appModule, networkModule
                )
            )
        }
    }

}