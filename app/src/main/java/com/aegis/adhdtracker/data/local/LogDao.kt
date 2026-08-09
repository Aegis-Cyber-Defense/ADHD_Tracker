package com.aegis.adhdtracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {
    @Insert
    suspend fun insertLog(log: DailyLogEntity)

    @Query("SELECT * FROM daily_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<DailyLogEntity>>
}
