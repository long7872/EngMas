package com.example.engmas.ui.screens.exam

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class QuestionAnswer(
    val questionId: Int,
    val selectedAnswer: String?,
    val correctAnswer: String
)

data class PartResult(
    val partNumber: Int,
    val questions: List<QuestionAnswer>
)

data class ToeicTestState(
    val examId: String = "",
    val selectedParts: List<Int> = (1..7).toList(),
    val currentPartIndex: Int = 0,
    val partResults: List<PartResult> = emptyList()
) {
    val totalScore: Int
        get() = partResults.sumOf { part ->
            part.questions.count { it.selectedAnswer == it.correctAnswer }
        }
}

class ExamViewModel : ViewModel() {
    private val _state = MutableStateFlow(ToeicTestState())
    val state: StateFlow<ToeicTestState> = _state

    fun startExam(examId: String, selectedParts: List<Int>) {
        _state.value = ToeicTestState(
            examId = examId,
            selectedParts = selectedParts,
            currentPartIndex = 0
        )
    }

    fun submitPart(partNumber: Int, answers: List<QuestionAnswer>) {
        val updated = _state.value.partResults
            .filterNot { it.partNumber == partNumber } +
                PartResult(partNumber, answers)

        _state.value = _state.value.copy(partResults = updated)
    }

    fun moveToNextPart() {
        _state.value = _state.value.copy(
            currentPartIndex = _state.value.currentPartIndex + 1
        )
    }

    fun getCurrentPart(): Int? {
        return _state.value.selectedParts.getOrNull(_state.value.currentPartIndex)
    }

    fun getPartResult(partNumber: Int): PartResult? {
        return _state.value.partResults.find { it.partNumber == partNumber }
    }

    fun reset() {
        _state.value = ToeicTestState()
    }
}

