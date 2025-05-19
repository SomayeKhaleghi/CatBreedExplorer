package com.challenge.catbreedexplorer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CatApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}