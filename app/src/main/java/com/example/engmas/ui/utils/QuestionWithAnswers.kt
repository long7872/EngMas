package com.example.engmas.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont

@Composable
fun QuestionWithAnswers(
    question: String,
    options: List<String>,
    selected: String = "",
    onClicked: (String) -> Unit = {},
) {
    var selectedAnswer by remember { mutableStateOf("") } // Track selected answer
    selectedAnswer = selected
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Question Text
        Text(
            text = question,
            fontFamily = KufamFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = Color(0xFF757575),
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // Options
        options.forEach { option ->
            val backgroundColor = if (option == selectedAnswer) {
                Color(0xFF8FE8FA)
            } else {
                Color(0xFFE3F2FD)
            }

            Button(
                onClick = {
                    selectedAnswer = option
                    onClicked(option)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                border = BorderStroke(2.dp, Color(0xFFD4D2D2)),
                elevation = ButtonDefaults.buttonElevation(2.dp),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = option,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    color = Color(0xFF757575),
                    fontFamily = KufamFont,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionWithAnswersPreview() {
    val question = "38. What event does the woman mention?"
    val options = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
    EngMasTheme {
        QuestionWithAnswers(question = question, options = options, onClicked = {})
    }
}
