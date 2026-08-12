package com.aegis.adhdtracker.data.health

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.*
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.aegis.adhdtracker.data.model.HealthMetrics
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthConnectManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    fun getSdkStatus(): Int {
        return HealthConnectClient.getSdkStatus(context)
    }

    suspend fun hasAllPermissions(): Boolean {
        if (getSdkStatus() != HealthConnectClient.SDK_AVAILABLE) return false
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        val required = setOf(
            HealthPermission.getReadPermission(HeartRateRecord::class),
            HealthPermission.getReadPermission(SleepSessionRecord::class)
        )
        return granted.containsAll(required)
    }

    suspend fun read24HourVitals(): HealthMetrics {
        val now = Instant.now()
        val startTime = now.minus(24, ChronoUnit.HOURS)
        val timeRange = TimeRangeFilter.between(startTime, now)

        return try {
            // 1. Average Heart Rate
            val hrResponse = healthConnectClient.readRecords(
                ReadRecordsRequest(HeartRateRecord::class, timeRangeFilter = timeRange)
            )
            val hrSamples = hrResponse.records.flatMap { it.samples }
            val avgHr = if (hrSamples.isNotEmpty()) hrSamples.map { it.beatsPerMinute }.average().toInt() else 0

            // 2. Heart Rate Variability (HRV RMSSD)
            val hrvResponse = healthConnectClient.readRecords(
                ReadRecordsRequest(HeartRateVariabilityRmssdRecord::class, timeRangeFilter = timeRange)
            )
            val hrvMs = hrvResponse.records.lastOrNull()?.heartRateVariabilityMillis

            // 3. Blood Pressure (Systolic / Diastolic)
            val bpResponse = healthConnectClient.readRecords(
                ReadRecordsRequest(BloodPressureRecord::class, timeRangeFilter = timeRange)
            )
            val bpRecord = bpResponse.records.lastOrNull()
            val bpPair = bpRecord?.let { Pair(it.systolic.inMillimetersOfMercury.toInt(), it.diastolic.inMillimetersOfMercury.toInt()) }

            // 4. Blood Oxygen Saturation (SpO2)
            val spO2Response = healthConnectClient.readRecords(
                ReadRecordsRequest(OxygenSaturationRecord::class, timeRangeFilter = timeRange)
            )
            val spO2Val = spO2Response.records.lastOrNull()?.percentage?.value

            // 5. Total Sleep Session Duration
            val sleepResponse = healthConnectClient.readRecords(
                ReadRecordsRequest(SleepSessionRecord::class, timeRangeFilter = timeRange)
            )
            val totalSleepMinutes = sleepResponse.records.sumOf { ChronoUnit.MINUTES.between(it.startTime, it.endTime) }
            val sleepHours = if (totalSleepMinutes > 0) totalSleepMinutes / 60.0 else null

            HealthMetrics(
                avgHeartRate = avgHr,
                hrvMs = hrvMs,
                bloodPressureSysDia = bpPair,
                spO2Percentage = spO2Val,
                sleepHours = sleepHours
            )
        } catch (e: Exception) {
            HealthMetrics()
        }
    }
}
