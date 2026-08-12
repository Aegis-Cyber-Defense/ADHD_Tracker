package com.aegis.adhdtracker.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val logs by viewModel.allLogs.collectAsState()
    val weeklyReview by viewModel.weeklyReview.collectAsState()
    val isReviewLoading by viewModel.isReviewLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkForSaturdayReview()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "History & Weekly Reviews",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Weekly Review (Saturday Auto-Trigger)",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (isReviewLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                } else if (weeklyReview.isNotBlank()) {
                    Text(text = weeklyReview, style = MaterialTheme.typography.bodyMedium)
                } else {
                    Text(
                        text = "Saturday review will automatically trigger here at the end of the week.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.triggerWeeklyReview() }) {
                        Text("Generate Weekly Review Now")
                    }
                }
            }
        }

        Text(
            text = "Daily Logs History",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (logs.isEmpty()) {
            Text("No previous daily logs found.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(logs) { log ->
                    val dateStr = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(Date(log.timestamp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = dateStr, style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Food: ${log.foodIntake}")
                            Text("Mood: ${log.emotionState}")
                            Text("Energy: ${log.energyLevel}/10")
                        }
                    }
                }
            }
        }
    }
}
