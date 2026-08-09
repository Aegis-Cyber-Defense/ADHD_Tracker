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

        Firebase.initialize(this)

        if (BuildConfig.DEBUG) {
            // Force static debug secret into SharedPreferences for development builds
            val debugSecret = "123e4567-e89b-12d3-a456-426614174000"
            val prefs = getSharedPreferences("com.google.firebase.appcheck.debug.STORE", Context.MODE_PRIVATE)
            prefs.edit().putString("com.google.firebase.appcheck.debug.DEBUG_SECRET", debugSecret).apply()

            Firebase.appCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
            )
        }
    }
}
