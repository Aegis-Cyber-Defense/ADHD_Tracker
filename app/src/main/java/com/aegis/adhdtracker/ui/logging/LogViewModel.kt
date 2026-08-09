package com.aegis.adhdtracker.ui.logging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aegis.adhdtracker.data.health.HealthConnectManager
import com.aegis.adhdtracker.data.local.DailyLogEntity
import com.aegis.adhdtracker.data.remote.GeminiService
import com.aegis.adhdtracker.data.repository.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogViewModel @Inject constructor(
    private val repository: LogRepository,
    private val healthConnectManager: HealthConnectManager,
    private val geminiService: GeminiService
) : ViewModel() {

    val logsState: StateFlow<List<DailyLogEntity>> = repository.allLogs.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _hasHealthPermissions = MutableStateFlow(false)
    val hasHealthPermissions: StateFlow<Boolean> = _hasHealthPermissions.asStateFlow()

    private val _aiInsight = MutableStateFlow<String?>(null)
    val aiInsight: StateFlow<String?> = _aiInsight.asStateFlow()

    private val _isLoadingInsight = MutableStateFlow(false)
    val isLoadingInsight: StateFlow<Boolean> = _isLoadingInsight.asStateFlow()

    fun checkPermissions() {
        viewModelScope.launch {
            _hasHealthPermissions.value = try {
                healthConnectManager.hasAllPermissions()
            } catch (e: Exception) {
                false
            }
        }
    }

    fun submitLog(food: String, emotion: String, energy: Int) {
        viewModelScope.launch {
            repository.saveDailyLog(food, emotion, energy)
        }
    }

    fun fetchAiRecoveryInsight() {
        viewModelScope.launch {
            _isLoadingInsight.value = true
            val currentLogs = logsState.value
            val heartRates = try {
                healthConnectManager.readRecentHeartRate()
            } catch (e: Exception) {
                emptyList()
            }

            val avgHeartRate = if (heartRates.isNotEmpty()) {
                heartRates.flatMap { it.samples }.map { it.beatsPerMinute }.average().toInt()
            } else {
                0
            }

            val foodList = currentLogs.map { it.foodIntake }.filter { it.isNotBlank() }
            val emotionList = currentLogs.map { it.emotionState }.filter { it.isNotBlank() }
            val energyList = currentLogs.map { it.energyLevel }

            val insight = geminiService.generateRecoveryInsight(
                foodLogs = foodList,
                emotionLogs = emotionList,
                energyLevels = energyList,
                avgHeartRate = avgHeartRate
            )

            _aiInsight.value = insight
            _isLoadingInsight.value = false
        }
    }
}
