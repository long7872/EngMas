package com.example.engmas.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vocab (
    @SerialName(value = "api_id")
    val id: Int,
    val word: String,
    val phonetic: String = "",
    @SerialName(value = "word_vi")
    val wordVi: String = ""
)