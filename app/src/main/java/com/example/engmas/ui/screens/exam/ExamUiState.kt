package com.example.engmas.ui.screens.exam

import com.example.engmas.ui.screens.exam.data.ToeicQuestion
import java.io.File

data class ExamUiState(
    val url: String = "",
    val rootFolder: File? = null,
    val listName: List<String> = emptyList(),
    val selectedExam: String = "",
    val screenState: ExamScreenState = ExamScreenState.Loading,
    val checkList: List<Boolean> = emptyList(),
    val selectedParts: Int = -1,
    val questionParts: Map<Int, List<ToeicQuestion>> = emptyMap(),
    val questionIndexInPart: Int = 0,
    val selectedAnswerParts: Map<Int, Map<String, List<String>>> = emptyMap(),
    val isDone: Boolean = false,
    val correctInParts: List<Int> = List(7) { 0 },
    val score: Int = 10,
)

enum class ExamScreenState {
    Loading,
    Main,
    Select,
    Part1,
    Part2,
    Part3,
    Part4,
    Part5,
    Part6,
    Part7,
    Result
}