package com.example.engmas.ui.screens.exam.part7

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.Previous_Next_Button
import com.example.engmas.ui.utils.QuestionWithAnswers
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.ZoomableImageCard

@Composable
fun Part7(
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
                    currentQuestion = "196-200",
                    totalQuestions = "200",
                    part = "7",
                    timeLeft = 7200
                )

                // LazyColumn for scrolling the questions
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    item {
                        ZoomableImageCard("https://drive.google.com/uc?export=view&id=1NP1x7xsto8TQpV524W3Dtrc3_v6jjaKK")
                    }
                    item {
                        ZoomableImageCard("https://drive.google.com/uc?export=view&id=1NP1x7xsto8TQpV524W3Dtrc3_v6jjaKK")
                    }
                    item {
                        ZoomableImageCard("https://drive.google.com/uc?export=view&id=1NP1x7xsto8TQpV524W3Dtrc3_v6jjaKK")
                    }
                    item {
                        val question1 = "71. What event does the woman mention?"
                        val options1 = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
                        QuestionWithAnswers(question = question1, options = options1)
                    }
                    item {
                        val question2 = "72. What event does the woman mention?"
                        val options2 = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
                        QuestionWithAnswers(question = question2, options = options2)
                    }
                    item {
                        val question3 = "73. What event does the woman mention?"
                        val options3 = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
                        QuestionWithAnswers(question = question3, options = options3)
                    }
                    item {
                        val question4 = "73. What event does the woman mention?"
                        val options4 = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
                        QuestionWithAnswers(question = question4, options = options4)
                    }
                    item {
                        val question5 = "73. What event does the woman mention?"
                        val options5 = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
                        QuestionWithAnswers(question = question5, options = options5)
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
private fun Part7Preview() {
    EngMasTheme {
        Part7()
    }
}