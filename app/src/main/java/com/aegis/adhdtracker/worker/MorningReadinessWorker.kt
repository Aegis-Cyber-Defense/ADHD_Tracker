package com.aegis.adhdtracker.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aegis.adhdtracker.data.health.HealthConnectManager
import com.aegis.adhdtracker.data.remote.GeminiService
import com.aegis.adhdtracker.util.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MorningReadinessWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val healthConnectManager: HealthConnectManager,
    private val geminiService: GeminiService
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // 1. Fetch overnight biometrics (sleep duration & HRV)
            val vitals = healthConnectManager.read24HourVitals()

            // 2. Request Gemini AI Morning Readiness Briefing
            val briefing = geminiService.generateMorningBriefing(vitals)

            // 3. Post local notification
            NotificationHelper.showMorningNotification(context, briefing)

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
