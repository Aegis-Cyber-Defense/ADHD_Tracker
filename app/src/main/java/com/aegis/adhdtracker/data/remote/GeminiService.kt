package com.aegis.adhdtracker.data.remote

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
        avgHeartRate: Int
    ): String = try {
        val prompt = """
            You are an empathetic, concise ADHD recovery coach. Analyze this daily health data:
            - Foods Consumed: ${foodLogs.joinToString(", ")}
            - Moods/Emotions: ${emotionLogs.joinToString(", ")}
            - Recorded Energy Levels: ${energyLevels.joinToString(", ")}/10
            - Galaxy Ring Avg Heart Rate (24h): ${if (avgHeartRate > 0) "$avgHeartRate bpm" else "No vitals recorded"}

            Provide 2 short, actionable observations on how food and vitals might be impacting mood and focus. Keep recommendations clear and under 100 words.
        """.trimIndent()

        val response = model.generateContent(prompt)
        response.text ?: "No insight generated at this time."
    } catch (e: Exception) {
        "Unable to generate AI insight: ${e.localizedMessage}"
    }
}
