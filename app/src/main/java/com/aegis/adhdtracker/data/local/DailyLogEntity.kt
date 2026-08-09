package com.aegis.adhdtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_logs")
data class DailyLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val foodIntake: String,
    val emotionState: String,
    val energyLevel: Int
)
