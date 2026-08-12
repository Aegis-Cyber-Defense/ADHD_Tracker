package com.aegis.adhdtracker.data.model

/**
 * Aggregated physiological metrics captured from Health Connect (e.g. Galaxy Watch Ultra 2).
 *
 * All values except [avgHeartRate] are nullable to represent "not recorded". [avgHeartRate]
 * uses 0 as its "not recorded" sentinel to match the existing heart-rate aggregation logic.
 */
data class HealthMetrics(
    val avgHeartRate: Int = 0,
    val hrvMs: Double? = null,
    val spO2Percentage: Double? = null,
    val sleepHours: Double? = null,
    val bloodPressureSysDia: Pair<Int, Int>? = null
)
