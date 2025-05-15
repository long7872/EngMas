package com.example.engmas.ui.screens.home.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VocabInfo(
    @SerialName(value = "api_id")
    val id: Int = -1,
    val word: String = "",
    @SerialName(value = "word_vi")
    val wordVi: String = "",
    val phonetics: List<Phonetic> = emptyList(),
    val meanings: List<Meaning> = emptyList(),
)

@Serializable
data class Phonetic(
    val text: String = "",
    val audio: String = "",
)

@Serializable
data class Meaning(
    val partOfSpeech: String = "",
    val definitions: List<Definition> = emptyList(),
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList(),
)

@Serializable
data class Definition(
    val definition: String = "",
    @SerialName(value = "definition_vi")
    val definitionVi: String = "",
    val example: String = "",
)
