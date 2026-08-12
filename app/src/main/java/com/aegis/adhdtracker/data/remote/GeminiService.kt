package com.aegis.adhdtracker.data.remote

import com.aegis.adhdtracker.data.model.HealthMetrics
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiService @Inject constructor() {

    private val model = Firebase.ai(backend = GenerativeBackend.googleAI()).generativeModel(
        modelName = "gemini-3.6-flash"
    )

    suspend fun generateRecoveryInsight(
        foodLogs: List<String>,
        emotionLogs: List<String>,
        energyLevels: List<Int>,
        vitals: HealthMetrics
    ): String = try {
        val bpText = vitals.bloodPressureSysDia?.let { "${it.first}/${it.second} mmHg" } ?: "Not recorded"
        val hrvText = vitals.hrvMs?.let { "${it.toInt()} ms" } ?: "Not recorded"
        val spO2Text = vitals.spO2Percentage?.let { "${it.toInt()}%" } ?: "Not recorded"
        val sleepText = vitals.sleepHours?.let { "$it hours" } ?: "Not recorded"
        val hrText = if (vitals.avgHeartRate > 0) "${vitals.avgHeartRate} bpm" else "Not recorded"

        val prompt = """
            You are an empathetic, expert ADHD recovery coach analyzing daily health data from a Galaxy Watch Ultra 2:

            - Food Intake: ${foodLogs.ifEmpty { listOf("None logged") }.joinToString(", ")}
            - Mood/Emotions: ${emotionLogs.ifEmpty { listOf("None logged") }.joinToString(", ")}
            - Energy Rating: ${energyLevels.ifEmpty { listOf(5) }.joinToString(", ")}/10
            - Avg Heart Rate: $hrText
            - Heart Rate Variability (HRV): $hrvText
            - Blood Pressure: $bpText
            - Blood Oxygen (SpO2): $spO2Text
            - Sleep Duration: $sleepText

            Analyze how these physiological metrics correlate with reported mood, energy, and ADHD focus. Provide 2-3 brief, actionable recovery recommendations under 120 words.
        """.trimIndent()

        val response = model.generateContent(prompt)
        response.text ?: "No insight generated at this time."
    } catch (e: Exception) {
        "Unable to generate AI insight: ${e.localizedMessage}"
    }
}
