package com.aegis.adhdtracker.data.model

data class HealthMetrics(
    val avgHeartRate: Int = 0,
    val hrvMs: Double? = null,
    val bloodPressureSysDia: Pair<Int, Int>? = null,
    val spO2Percentage: Double? = null,
    val sleepHours: Double? = null,
    val samsungHealthFood: List<String> = emptyList()
)
