package com.example.engmas.ui.screens.exam.part5

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.engmas.R
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.utils.AnswerButtons
import com.example.engmas.ui.utils.AudioPlayer
import com.example.engmas.ui.utils.Previous_Next_Button
import com.example.engmas.ui.utils.QuestionWithAnswerFeedback
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.ZoomableImageCard

@Composable
fun Part5_Result(
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
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                QuizHeader(
                    currentQuestion = "130",
                    totalQuestions = "200",
                    part = "5",
                    timeLeft = 7200
                )
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    val question = "130. The_______information provided by Uniss Bank’s brochure helps applicants understand the terms of their loans."
                    val options = listOf(
                        "A. arbitrary", "B. supplemental", "C. superfluous", "D. potential"
                    )
                    val correctAnswer = "B. supplemental"
                    val userAnswer = "C. superfluous"

                    QuestionWithAnswerFeedback(
                        question = question,
                        options = options,
                        correctAnswer = correctAnswer,
                        userAnswer = userAnswer
                    )
                }
                Previous_Next_Button()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Part5_ResultPreview() {
    EngMasTheme {
        Part5_Result()
    }
}