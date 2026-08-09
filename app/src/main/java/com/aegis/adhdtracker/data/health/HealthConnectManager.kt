package com.aegis.adhdtracker.data.health

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
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

    suspend fun hasAllPermissions(): Boolean {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        val required = setOf(
            androidx.health.connect.client.permission.HealthPermission.getReadPermission(HeartRateRecord::class),
            androidx.health.connect.client.permission.HealthPermission.getReadPermission(SleepSessionRecord::class)
        )
        return granted.containsAll(required)
    }

    suspend fun readRecentHeartRate(): List<HeartRateRecord> {
        val startTime = Instant.now().minus(24, ChronoUnit.HOURS)
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                recordType = HeartRateRecord::class,
                timeRangeFilter = TimeRangeFilter.after(startTime)
            )
        )
        return response.records
    }
}
