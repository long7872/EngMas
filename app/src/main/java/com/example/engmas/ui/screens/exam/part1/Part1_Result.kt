package com.example.engmas.ui.screens.exam.part1

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.data.QuestionData
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.utils.AnswerButtons
import com.example.engmas.ui.utils.AudioPlayer
import com.example.engmas.ui.utils.Previous_Next_Button
import com.example.engmas.ui.utils.QuestionWithAnswerFeedback
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.ZoomableImageCard

@Composable
fun Part1_Result(
    part1ViewModel: Part1ViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by part1ViewModel.uiState.collectAsState()

    // Tính điểm
    val totalPoints = calculateTotalPoints(uiState.questionsPart1)

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                QuizHeader(
                    currentQuestion = (uiState.currentQuestionIndex + 1).toString(),
                    totalQuestions = uiState.questionsPart1.size.toString(),
                    part = "1",
                    timeLeft = 7200
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    itemsIndexed(uiState.questionsPart1) { index, questionData ->
                        // Hiển thị hình ảnh nếu có
                        ZoomableImageCard(questionData.imageFile?.toURI().toString())

                        // Hiển thị audio nếu có
                        AudioPlayer(questionData.audioFile?.toURI().toString())

                        // Hiển thị câu hỏi và các đáp án
                        QuestionWithAnswerFeedback(
                            question = questionData.question,
                            options = questionData.options,
                            correctAnswer = questionData.correctAnswer,
                            userAnswer = questionData.userAnswer ?: "No Answer"
                        )
                    }
                    item {
                        Previous_Next_Button()
                    }
                }
            }
        }
    }
    Log.d("Total point part 1", "$totalPoints")
}

fun calculateTotalPoints(questions: List<QuestionData>): Int {
    var totalPoints = 0
    questions.forEach { question ->
        if (question.userAnswer == question.correctAnswer) {
            totalPoints += 5  // Mỗi câu đúng được 5 điểm
        }
    }
    return totalPoints
}

@Preview(showBackground = true)
@Composable
private fun Part1_ResultPreview() {
    EngMasTheme {
        Part1_Result()
    }
}