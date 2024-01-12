package com.example.travel_taipei

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
    companion object {
        lateinit var appResources: Resources
    }
    override fun onCreate() {
        super.onCreate()

        appResources = this.resources

        // 若目前在開發狀態，初始化Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}