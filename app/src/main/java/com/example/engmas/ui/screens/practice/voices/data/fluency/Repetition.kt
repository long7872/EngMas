package com.example.engmas.ui.screens.practice.voices.data.fluency

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repetition(
    val text: String,
    @SerialName(value = "start_index")
    val startIndex: Int,
    @SerialName(value = "end_index")
    val endIndex: Int
)