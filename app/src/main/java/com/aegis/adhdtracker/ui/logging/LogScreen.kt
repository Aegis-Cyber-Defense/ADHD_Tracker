package com.aegis.adhdtracker.ui.logging

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LogScreen(
    viewModel: LogViewModel = hiltViewModel()
) {
    var foodText by remember { mutableStateOf("") }
    var emotionText by remember { mutableStateOf("") }
    var energySlider by remember { mutableFloatStateOf(5f) }

    val aiInsight by viewModel.aiInsight.collectAsState()
    val isLoadingInsight by viewModel.isLoadingInsight.collectAsState()
    val logsState by viewModel.logsState.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Daily ADHD Recovery Tracker",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = foodText,
            onValueChange = { foodText = it },
            label = { Text("Food / Diet Intake") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = emotionText,
            onValueChange = { emotionText = it },
            label = { Text("Emotions / Mood") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Energy Level: ${energySlider.toInt()}/10")
        Slider(
            value = energySlider,
            onValueChange = { energySlider = it },
            valueRange = 1f..10f,
            steps = 8
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    viewModel.fetchAiRecoveryInsight()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isLoadingInsight) "Analyzing..." else "Get Gemini Insights")
            }

            Button(
                onClick = {
                    viewModel.submitLog(foodText, emotionText, energySlider.toInt())
                    foodText = ""
                    emotionText = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save Entry")
            }
        }

        if (aiInsight.orEmpty().isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Gemini Recovery Insight",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = aiInsight.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        if (logsState.isNotEmpty()) {
            Text(
                text = "Recent Logs",
                style = MaterialTheme.typography.titleLarge
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                logsState.take(5).forEach { log ->
                    val dateStr = SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(Date(log.timestamp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = dateStr, style = MaterialTheme.typography.labelMedium)
                            Text("Food: ${log.foodIntake ?: "None logged"}")
                            Text("Mood: ${log.emotionState ?: "None logged"}")
                            Text("Energy: ${log.energyLevel}/10")
                        }
                    }
                }
            }
        }
    }
}
