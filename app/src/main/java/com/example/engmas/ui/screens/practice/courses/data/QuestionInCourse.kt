package com.example.engmas.ui.screens.practice.courses.data

import com.example.engmas.data.model.Course
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionInCourse(
    val course: Course = Course(),
    @SerialName(value = "vocabulary_questions")
    val vocabQuestions: List<QuestionVocabItem> = emptyList(),
    @SerialName(value = "grammar_questions")
    val grammarQuestions: List<QuestionGrammarItem> = emptyList(),
)

@Serializable
data class QuestionVocabItem(
    @SerialName(value = "api_id")
    val id: Int = -1,
    val word: String = "",
    @SerialName(value = "word_vi")
    val wordVi: String = "",
    val phonetic: String = "",
    val audio: String = "",
    val status: QuestionStatus = QuestionStatus.Learning,
)

@Serializable
data class QuestionGrammarItem(
    @SerialName(value = "api_id")
    val id: Int = -1,
    @SerialName(value = "grammar_name")
    val grammarName: String = "",
    val question: String = "",
    val answer: String = "",
    val status: QuestionStatus = QuestionStatus.Learning,
)

enum class QuestionStatus {
    Learning,
    Review,
    Known
}
