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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.engmas.ui.screens.exam.data.Question
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.utils.AnswerButtons
import com.example.engmas.ui.utils.AudioPlayer
import com.example.engmas.ui.utils.Previous_Next_Button
import com.example.engmas.ui.utils.QuestionWithAnswerFeedback
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.ZoomableImageCard
import java.io.File

@Composable
fun Part1_Result(
    currentQuestion: String,
    audioFile: File?,
    imageFiles: List<File>,
    question: Question,
    selectedAnswer: String?,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuizHeader(
                    currentQuestion = currentQuestion,
                    part = "1",
                    timeLeft = 7200
                )

                imageFiles.forEach { imageFile ->
                    imageFile.let {
                        Log.d("Part1", "Loading image: ${it.toURI()}")
                        ZoomableImageCard(imageUrl = it.toURI().toString())
                    }
                }
                AudioPlayer(audioFile?.toURI().toString())
                QuestionWithAnswerFeedback(
                    question = question.question,
                    options = question.options,
                    correctAnswer = question.correctAnswer,
                    userAnswer = selectedAnswer ?: "No Answer"
                )
                Previous_Next_Button(
                    onPreviousClicked = onPreviousClicked,
                    onNextClicked = onNextClicked
                )
            }
        }
    }
}

//fun calculateTotalPoints(questions: List<QuestionData>): Int {
//    var totalPoints = 0
//    questions.forEach { question ->
//        if (question.userAnswer == question.correctAnswer) {
//            totalPoints += 5  // Mỗi câu đúng được 5 điểm
//        }
//    }
//    return totalPoints
//}

@Preview(showBackground = true)
@Composable
private fun Part1_ResultPreview() {
    EngMasTheme {
//        Part1_Result()
    }
}