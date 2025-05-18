package com.example.engmas.ui.screens.practice.voices.data.pronunciation

import com.example.engmas.ui.screens.practice.voices.data.english_proficiency_scores.EnglishProficiencyScores
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pronunciation(
    val words: List<PronouncedWord> = emptyList(),
    @SerialName(value = "overall_score")
    val overallScore: Float = 0.0f,
    @SerialName(value = "expected_text")
    val expectedText: String = "",
    @SerialName(value = "english_proficiency_scores")
    val englishProficiencyScores: EnglishProficiencyScores = EnglishProficiencyScores(),
    val warnings: Map<String, String> = emptyMap(),
    @SerialName(value = "lowest_scoring_phonemes")
    val lowestScoringPhonemes: List<Phoneme> = emptyList()
)
