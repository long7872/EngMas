package com.example.engmas.ui.screens.practice.voices.data.fluency

import com.example.engmas.ui.screens.practice.voices.data.english_proficiency_scores.EnglishProficiencyScores
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fluency(
    @SerialName(value = "overall_score")
    val overallScore: Float = 0.0f,
    val metrics: FluencyMetrics = FluencyMetrics(),
    @SerialName(value = "english_proficiency_scores")
    val englishProficiencyScores: EnglishProficiencyScores = EnglishProficiencyScores(),
    val warnings: Map<String, String> = emptyMap(),
    val feedback: FluencyFeedback = FluencyFeedback()
)

