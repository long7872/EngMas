package com.example.engmas.ui.screens.practice.voices.data.pronunciation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PronouncedWord(
    @SerialName(value = "word_text")
    val wordText: String = "",
    val phonemes: List<Phoneme> = emptyList(),
    @SerialName(value = "word_score")
    val wordScore: Int = 0
)
