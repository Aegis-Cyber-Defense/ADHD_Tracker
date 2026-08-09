package com.aegis.adhdtracker.data.repository

import com.aegis.adhdtracker.data.local.DailyLogEntity
import com.aegis.adhdtracker.data.local.LogDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogRepository @Inject constructor(
    private val logDao: LogDao
) {
    val allLogs: Flow<List<DailyLogEntity>> = logDao.getAllLogs()

    suspend fun saveDailyLog(food: String, emotion: String, energy: Int) {
        val log = DailyLogEntity(
            foodIntake = food,
            emotionState = emotion,
            energyLevel = energy
        )
        logDao.insertLog(log)
    }
}
