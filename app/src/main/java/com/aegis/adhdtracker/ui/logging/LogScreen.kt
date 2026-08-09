package com.aegis.adhdtracker.ui.logging

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aegis.adhdtracker.ui.health.HealthPermissionButton
import kotlin.math.roundToInt

@Composable
fun LogScreen(
    viewModel: LogViewModel = hiltViewModel()
) {
    val logs by viewModel.logsState.collectAsState()
    val aiInsight by viewModel.aiInsight.collectAsState()
    val isLoadingInsight by viewModel.isLoadingInsight.collectAsState()
    val hasHealthPermissions by viewModel.hasHealthPermissions.collectAsState()

    var foodInput by remember { mutableStateOf("") }
    var emotionInput by remember { mutableStateOf("") }
    var energyInput by remember { mutableFloatStateOf(5f) }

    LaunchedEffect(Unit) {
        viewModel.checkPermissions()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Daily ADHD Recovery Tracker",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (!hasHealthPermissions) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sync Galaxy Ring Vitals",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Allow Health Connect access to correlate heart rate and sleep with your daily logs.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    HealthPermissionButton(
                        onPermissionsGranted = {
                            viewModel.checkPermissions()
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = foodInput,
            onValueChange = { foodInput = it },
            label = { Text("Food / Diet Intake") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = emotionInput,
            onValueChange = { emotionInput = it },
            label = { Text("Emotions / Mood") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Energy Level: ${energyInput.roundToInt()}/10")
        Slider(
            value = energyInput,
            onValueChange = { energyInput = it },
            valueRange = 1f..10f,
            steps = 8
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { viewModel.fetchAiRecoveryInsight() },
                enabled = !isLoadingInsight
            ) {
                if (isLoadingInsight) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Get Gemini Insights")
                }
            }

            Button(
                onClick = {
                    if (foodInput.isNotBlank() || emotionInput.isNotBlank()) {
                        viewModel.submitLog(
                            food = foodInput,
                            emotion = emotionInput,
                            energy = energyInput.roundToInt()
                        )
                        foodInput = ""
                        emotionInput = ""
                    }
                }
            ) {
                Text("Save Entry")
            }
        }

        aiInsight?.let { insightText ->
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Gemini Recovery Insight",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = insightText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Text(
            text = "Recent Logs",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(logs) { log ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = "Food: ${log.foodIntake}")
                        Text(text = "Emotion: ${log.emotionState}")
                        Text(text = "Energy Level: ${log.energyLevel}/10")
                    }
                }
            }
        }
    }
}
