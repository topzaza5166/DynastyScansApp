package com.dynasty.dynastyscansapp

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dynasty.dynastyscansapp.di.appModule
import com.dynasty.dynastyscansapp.di.database
import com.dynasty.dynastyscansapp.di.networkModule
import com.dynasty.dynastyscansapp.ui.woker.NotificationWorker
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class MainApplication : Application() {

    private val request =
        PeriodicWorkRequestBuilder<NotificationWorker>(2, TimeUnit.HOURS).build()

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    appModule, networkModule, database
                )
            )
        }

        WorkManager.getInstance().enqueue(request)
    }

    override fun onTerminate() {
        super.onTerminate()
        WorkManager.getInstance().cancelWorkById(request.id)
    }

}