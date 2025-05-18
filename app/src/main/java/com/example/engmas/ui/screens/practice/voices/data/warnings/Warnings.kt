package com.example.engmas.ui.screens.practice.voices.data.warnings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Warnings(
    @SerialName(value = "NotEnoughWordsSpokenWarning")
    val notEnoughWordsSpokenWarning: String = ""
)