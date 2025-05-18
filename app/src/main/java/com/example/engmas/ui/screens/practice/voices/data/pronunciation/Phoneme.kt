package com.example.engmas.ui.screens.practice.voices.data.pronunciation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Phoneme(
    @SerialName(value = "ipa_label")
    val ipaLabel: String,
    @SerialName(value = "phoneme_score")
    val phonemeScore: Float
)
