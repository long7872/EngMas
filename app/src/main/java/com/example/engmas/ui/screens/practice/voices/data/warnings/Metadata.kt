package com.example.engmas.ui.screens.practice.voices.data.warnings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    @SerialName(value = "predicted_text")
    val predictedText: String = "",
    @SerialName(value = "content_relevance")
    val contentRelevance: Int = 0
)