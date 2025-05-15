package com.example.engmas.ui.screens.exam.part4

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.engmas.ui.screens.exam.data.Question
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.utils.AudioPlayer
import com.example.engmas.ui.utils.Previous_Next_Button
import com.example.engmas.ui.utils.QuestionWithAnswerFeedback
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.Reading
import com.example.engmas.ui.utils.ZoomableImageCard
import java.io.File

@Composable
fun Part4_Result(
    timeLeft: Long,
    currentQuestion: String,
    audioFile: File?,
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
                    part = "4",
                    timeLeft = timeLeft
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    items(imageFiles) { file ->
                        file.let {
                            Log.d("Part3", "Loading image: ${it.toURI()}")
                            ZoomableImageCard(imageUrl = it.toURI().toString())
                        }
                    }
                    item {
                        AudioPlayer(audioFile?.toURI().toString())
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
private fun Part4_ResultPreview() {
    EngMasTheme {
//        Part4_Result()
    }
}