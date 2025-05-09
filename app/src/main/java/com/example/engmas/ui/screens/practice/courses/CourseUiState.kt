package com.example.engmas.ui.screens.practice.courses

import com.example.engmas.data.model.Course
import com.example.engmas.ui.screens.practice.courses.data.QuestionGrammarItem
import com.example.engmas.ui.screens.practice.courses.data.QuestionVocabItem

data class CourseUiState(
    val selectedCourse: Course = Course(),
    val vocabQuestions: List<QuestionVocabItem> = emptyList(),
    val selectedVocab: QuestionVocabItem = QuestionVocabItem(),
    val answerList: List<String> = emptyList(),
    val grammarQuestions: List<QuestionGrammarItem> = emptyList(),
    val selectedGrammar: QuestionGrammarItem = QuestionGrammarItem(),
    val screenState: CourseScreenState = CourseScreenState.List
)

enum class CourseScreenState {
    List,
    Vocabulary,
    Grammar
}
