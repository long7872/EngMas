package com.example.engmas.ui.screens.practice.voices.data.warnings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reading(
    @SerialName(value = "total_time")
    val totalTime: Float = 0.0f,
    @SerialName(value = "words_read")
    val wordsRead: Int = 0,
    @SerialName(value = "unique_words_read")
    val uniqueWordsRead: Int = 0,
    @SerialName(value = "speed_wpm_correct")
    val speedWpmCorrect: Float = 0.0f,
    val completion: Float = 0.0f,
    @SerialName(value = "reading_time")
    val readingTime: Float = 0.0f,
    @SerialName(value = "non_reading_time")
    val nonReadingTime: Float = 0.0f,
    @SerialName(value = "words_assigned")
    val wordsAssigned: Int = 0,
    @SerialName(value = "unique_words_assigned")
    val uniqueWordsAssigned: Int = 0,
    @SerialName(value = "correct_words_read")
    val correctWordsRead: Int = 0,
    @SerialName(value = "words_added")
    val wordsAdded: Int = 0,
    @SerialName(value = "words_missed")
    val wordsMissed: Int = 0,
    @SerialName(value = "words_replaced")
    val wordsReplaced: Int = 0,
    @SerialName(value = "speed_wpm")
    val speedWpm: Float = 0.0f,
    val accuracy: Float = 0.0f
)