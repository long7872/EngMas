package com.example.engmas.ui.screens.practice.vocabulary.model

import com.example.engmas.data.model.Topic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VocabsInTopic(
    val topic: Topic = Topic(),
    val vocabs: List<VocabLearningInTopic> = emptyList()
)

@Serializable
data class VocabLearningInTopic(
    @SerialName(value = "api_id")
    val id: Int = -1,
    val word: String = "",
    @SerialName(value = "word_vi")
    val wordVi: String = "",
    val phonetic: String = "",
    @SerialName(value = "part_of_speech")
    val partOfSpeech: String = "",
    val definition: String = "",
    val status: VocabLearningStatus = VocabLearningStatus.Explore,
    val audio: String = ""
)

enum class VocabLearningStatus {
    Done,
    Explore
}
