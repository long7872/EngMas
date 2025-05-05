package com.example.engmas.ui.screens.exam.part3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.engmas.ui.utils.QuestionWithAnswers
import com.example.engmas.ui.utils.QuizHeader

@Composable
fun Part3(
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
                    currentQuestion = "38-40",
                    totalQuestions = "200",
                    part = "3",
                    timeLeft = 7200
                )

                // LazyColumn for scrolling the questions
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    item {
                        val question1 = "38. What event does the woman mention?"
                        val options1 = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
                        QuestionWithAnswers(question = question1, options = options1)
                    }
                    item {
                        val question2 = "39. What event does the woman mention?"
                        val options2 = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
                        QuestionWithAnswers(question = question2, options = options2)
                    }
                    item {
                        val question3 = "40. What event does the woman mention?"
                        val options3 = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
                        QuestionWithAnswers(question = question3, options = options3)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Part3Preview() {
    EngMasTheme {
        Part3()
    }
}