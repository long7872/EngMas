package com.example.engmas.ui.screens.practice.voices.data.english_proficiency_scores

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnglishProficiencyScores(
    @SerialName(value = "mock_ielts")
    val mockIelts: PredictionScore = PredictionScore(),
    @SerialName(value = "mock_cefr")
    val mockCefr: CEFRPrediction = CEFRPrediction(),
    @SerialName(value = "mock_pte")
    val mockPte: PredictionScore = PredictionScore()
)