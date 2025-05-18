package com.example.engmas.ui.screens.practice.voices.data.fluency

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FluencyFeedback(
    @SerialName(value = "speech_rate")
    val speechRate: FeedbackDetail = FeedbackDetail(),
    val pauses: FeedbackDetail = FeedbackDetail(),
    @SerialName(value = "filler_words")
    val fillerWords: FeedbackDetail = FeedbackDetail(),
    @SerialName(value = "tagged_transcript")
    val taggedTranscript: String = ""
)