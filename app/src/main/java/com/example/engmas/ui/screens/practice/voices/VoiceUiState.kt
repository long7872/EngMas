package com.example.engmas.ui.screens.practice.voices

import com.example.engmas.ui.screens.practice.voices.data.VoiceAnalysisResponse

data class VoiceUiState(
    val sentences: List<String> = emptyList(),
    val selectedSentence: String = "",
    val analyzedResult: VoiceAnalysisResponse = VoiceAnalysisResponse(),
    val currentSentence: Int = 0,
    val screenState: VoiceScreenState = VoiceScreenState.Record,
    val resultState: FeedbackTab = FeedbackTab.ContentRelevance,
)

enum class VoiceScreenState {
    Record,
    Analysis
}

enum class FeedbackTab(val label: String) {
    ContentRelevance("Content Relevance"),
    Pronunciation("Pronunciation"),
    Fluency("Fluency"),
    Reading("Reading")
}