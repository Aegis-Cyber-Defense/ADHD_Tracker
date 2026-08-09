package com.aegis.adhdtracker.ui.logging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aegis.adhdtracker.data.local.DailyLogEntity
import com.aegis.adhdtracker.data.repository.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogViewModel @Inject constructor(
    private val repository: LogRepository
) : ViewModel() {

    val logsState: StateFlow<List<DailyLogEntity>> = repository.allLogs.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun submitLog(food: String, emotion: String, energy: Int) {
        viewModelScope.launch {
            repository.saveDailyLog(food, emotion, energy)
        }
    }
}
