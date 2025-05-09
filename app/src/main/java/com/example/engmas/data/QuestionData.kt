package com.example.engmas.data

import java.io.File

data class QuestionData(
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val userAnswer: String? = null,
    val audioFile: File,
    val imageFile: File
)
