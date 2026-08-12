package com.aegis.adhdtracker.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aegis.adhdtracker.data.local.DailyLogEntity
import com.aegis.adhdtracker.data.remote.GeminiService
import com.aegis.adhdtracker.data.repository.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: LogRepository,
    private val geminiService: GeminiService
) : ViewModel() {

    val allLogs: StateFlow<List<DailyLogEntity>> = repository.allLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _weeklyReview = MutableStateFlow("")
    val weeklyReview: StateFlow<String> = _weeklyReview.asStateFlow()

    private val _isReviewLoading = MutableStateFlow(false)
    val isReviewLoading: StateFlow<Boolean> = _isReviewLoading.asStateFlow()

    fun checkForSaturdayReview() {
        val calendar = Calendar.getInstance()
        val isSaturday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY

        if (isSaturday && _weeklyReview.value.isBlank() && !_isReviewLoading.value) {
            triggerWeeklyReview()
        }
    }

    fun triggerWeeklyReview() {
        viewModelScope.launch {
            val logs = allLogs.value
            if (logs.isEmpty()) {
                _weeklyReview.value = "No daily logs recorded this week to generate a review."
                return@launch
            }

            _isReviewLoading.value = true
            val reviewText = geminiService.generateWeeklyReview(logs.take(7))
            _weeklyReview.value = reviewText
            _isReviewLoading.value = false
        }
    }
}
