package com.example.engmas.ui.screens.practice.vocabulary.model

import com.example.engmas.data.model.Topic
import com.example.engmas.ui.screens.practice.courses.data.QuestionVocabItem
import kotlinx.serialization.Serializable

@Serializable
data class VocabsInTopic(
    val topic: Topic = Topic(),
    val vocabs: List<QuestionVocabItem> = emptyList()
)