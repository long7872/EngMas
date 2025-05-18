package com.example.engmas.ui.screens.practice.voices.data.fluency

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FluencyMetrics(
    @SerialName(value = "speech_rate")
    val speechRate: Int = 0,
    @SerialName(value = "speech_rate_over_time")
    val speechRateOverTime: List<Int> = emptyList(),
    val pauses: Int = 0,
    @SerialName(value = "filler_words")
    val fillerWords: Int = 0,
    @SerialName(value = "discourse_markers")
    val discourseMarkers: List<String> = emptyList(),
    @SerialName(value = "filler_words_per_min")
    val fillerWordsPerMin: Float = 0.0f,
    @SerialName(value = "pause_details")
    val pauseDetails: List<String> = emptyList(),
    val repetitions: List<Repetition> = emptyList(),
    @SerialName(value = "filler_words_details")
    val fillerWordsDetails: List<String> = emptyList()
)
