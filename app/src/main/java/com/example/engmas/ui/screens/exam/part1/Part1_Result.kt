package com.example.engmas.ui.screens.exam.part1

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
fun Part1_Result(
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
                    currentQuestion = "6",
                    totalQuestions = "200",
                    part = "1",
                    timeLeft = 7200
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    item {
                        ZoomableImageCard("https://drive.google.com/uc?export=view&id=1NP1x7xsto8TQpV524W3Dtrc3_v6jjaKK")
                    }
                    item {
                        AudioPlayer(url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
                    }
                    item {
                        val question = null
                        val options = listOf(
                            "A.  Potted plants have been suspended from a ceiling.",
                            "B.  Chairs have been stacked in front of an entryway.",
                            "C.  A computer station has been set up on a desk.",
                            "D.  A rug has been rolled up against a wall."
                        )
                        val correctAnswer = "C.  A computer station has been set up on a desk."
                        val userAnswer = "A.  Potted plants have been suspended from a ceiling."

                        QuestionWithAnswerFeedback(
                            question = question,
                            options = options,
                            correctAnswer = correctAnswer,
                            userAnswer = userAnswer
                        )
                    }
                    item {
                        Previous_Next_Button()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Part1_ResultPreview() {
    EngMasTheme {
        Part1_Result()
    }
}