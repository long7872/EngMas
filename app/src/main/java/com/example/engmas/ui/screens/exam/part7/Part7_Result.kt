package com.example.engmas.ui.screens.exam.part7

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.screens.exam.data.Question
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.Previous_Next_Button
import com.example.engmas.ui.utils.QuestionWithAnswerFeedback
import com.example.engmas.ui.utils.QuestionWithAnswers
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.ZoomableImageCard
import java.io.File

@Composable
fun Part7_Result(
    currentQuestion: String,
    imageFiles: List<File>,
    questions: List<Question>,
    selectedAnswer: List<String>,
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
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                QuizHeader(
                    currentQuestion = currentQuestion,
                    part = "7",
                    timeLeft = 7200
                )

                // LazyColumn for scrolling the questions
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    items(imageFiles) { file ->
                        file.let {
                            Log.d("Part7", "Loading image: ${it.toURI()}")
                            ZoomableImageCard(imageUrl = it.toURI().toString())
                        }
                    }
                    itemsIndexed(questions) { index, item ->
                        val answer = selectedAnswer.getOrElse(index) { "" }
                        QuestionWithAnswerFeedback(
                            question = item.question,
                            options = item.options,
                            correctAnswer = item.correctAnswer,
                            userAnswer = answer
                        )
                    }
                    item {
                        Previous_Next_Button(
                            onPreviousClicked = onPreviousClicked,
                            onNextClicked = onNextClicked
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Part7_ResultPreview() {
    EngMasTheme {
//        Part7_Result()
    }
}