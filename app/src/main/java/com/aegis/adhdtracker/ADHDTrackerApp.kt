package com.aegis.adhdtracker

import android.app.Application
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ADHDTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Write debug secret token to storage before initializing Firebase
            val debugSecret = "8f3b2a1c-9d8e-4f7a-b6c5-d4e3f2a10b9c"
            val prefs = getSharedPreferences("com.google.firebase.appcheck.debug.STORE", Context.MODE_PRIVATE)
            prefs.edit().putString("com.google.firebase.appcheck.debug.DEBUG_SECRET", debugSecret).commit()

            Firebase.appCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
            )
        }

        Firebase.initialize(this)
    }
}
