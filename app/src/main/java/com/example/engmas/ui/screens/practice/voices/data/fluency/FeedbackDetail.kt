package com.example.engmas.ui.screens.practice.voices.data.fluency

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackDetail(
    @SerialName(value = "feedback_code")
    val feedbackCode: String = "",
    @SerialName(value = "feedback_text")
    val feedbackText: String = ""
)