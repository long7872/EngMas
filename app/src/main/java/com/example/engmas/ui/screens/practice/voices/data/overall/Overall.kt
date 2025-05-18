package com.example.engmas.ui.screens.practice.voices.data.overall

import com.example.engmas.ui.screens.practice.voices.data.english_proficiency_scores.EnglishProficiencyScores
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Overall(
    @SerialName(value = "english_proficiency_scores")
    val englishProficiencyScores: EnglishProficiencyScores = EnglishProficiencyScores(),
    @SerialName(value = "overall_score")
    val overallScore: Float = 0.0f
)