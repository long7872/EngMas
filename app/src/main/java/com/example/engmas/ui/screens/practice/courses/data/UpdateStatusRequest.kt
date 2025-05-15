package com.example.engmas.ui.screens.practice.courses.data

import kotlinx.serialization.Serializable

@Serializable
data class UpdateStatusRequest(
    val userId: String = "",
    val courseId: Int = -1,
    val vocabQuestions: List<QuestionVocabItem> = emptyList(),
    val grammarQuestions: List<QuestionGrammarItem> = emptyList()
)
