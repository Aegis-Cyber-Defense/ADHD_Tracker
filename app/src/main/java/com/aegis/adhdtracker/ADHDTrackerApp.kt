package com.aegis.adhdtracker

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ADHDTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase services cleanly without sending unverified debug tokens
        Firebase.initialize(this)
    }
}
