package com.example.engmas.ui.screens.exam

import com.example.engmas.data.QuestionData

data class ExamUiState(
    val currentQuestionIndex: Int = 0,
    val questionsPart1: List<QuestionData> = emptyList(),
    val currentAnswer: String? = null,
    val selectedAnswers: MutableList<String> = mutableListOf()
)
