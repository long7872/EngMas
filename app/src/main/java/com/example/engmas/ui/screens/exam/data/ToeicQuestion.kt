package com.example.engmas.ui.screens.exam.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

data class ToeicQuestion(
    val name: String = "",
    val audioFile: File? = null,
    val imageFiles: List<File> = emptyList(),
    val questions: List<Question> = emptyList()
)

@Serializable
data class Question(
    val question: String? = "",
    val options: List<String> = emptyList(),
    @SerialName(value = "correct_answer")
    val correctAnswer: String = "",
)
