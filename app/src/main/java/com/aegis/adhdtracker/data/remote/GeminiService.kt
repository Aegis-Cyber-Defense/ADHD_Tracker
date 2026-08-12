package com.aegis.adhdtracker.data.remote

import com.aegis.adhdtracker.data.local.DailyLogEntity
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

        // Combine manual app food entries with Samsung Health nutrition records
        val allFoods = (foodLogs + vitals.samsungHealthFood).filter { it.isNotBlank() }.distinct()
        val foodSummary = if (allFoods.isNotEmpty()) allFoods.joinToString(", ") else "None logged"

        val prompt = """
            You are an empathetic, expert ADHD recovery coach analyzing daily health data from a Galaxy Watch Ultra 2 & Samsung Health:

            - Nutrition / Foods Consumed: $foodSummary
            - Mood/Emotions: ${emotionLogs.ifEmpty { listOf("None logged") }.joinToString(", ")}
            - Energy Rating: ${energyLevels.ifEmpty { listOf(5) }.joinToString(", ")}/10
            - Avg Heart Rate: $hrText
            - Heart Rate Variability (HRV): $hrvText
            - Blood Pressure: $bpText
            - Blood Oxygen (SpO2): $spO2Text
            - Sleep Duration: $sleepText

            Analyze how these nutritional and physiological metrics correlate with reported mood, energy, and ADHD focus. Provide 2-3 brief, actionable recovery recommendations under 120 words.
            Formatting rule: Output clean plain text with standard numbered points. Do NOT use markdown headers (like ### or ##).
        """.trimIndent()

        val response = model.generateContent(prompt)
        response.text ?: "No insight generated at this time."
    } catch (e: Exception) {
        "Unable to generate AI insight: ${e.localizedMessage}"
    }

    suspend fun generateWeeklyReview(
        weeklyLogs: List<DailyLogEntity>
    ): String = try {
        val logSummary = weeklyLogs.joinToString("\n") { log ->
            "- Food: ${log.foodIntake ?: "None"}, Mood: ${log.emotionState ?: "None"}, Energy: ${log.energyLevel}/10"
        }

        val prompt = """
            You are an empathetic ADHD coach reviewing a full week of recovery data:

            $logSummary

            Provide a concise weekly review with 2 sections:
            1. What Went Well: Celebrate positive patterns in mood, nutrition, or energy stability.
            2. How to Improve: Give 2 low-friction, practical tweaks for next week.

            Keep total length under 150 words. Do NOT use markdown headers (such as ### or ##). Output clean plain text only.
        """.trimIndent()

        val response = model.generateContent(prompt)
        response.text ?: "Unable to generate weekly review."
    } catch (e: Exception) {
        "Unable to generate weekly review: ${e.localizedMessage}"
    }
}
