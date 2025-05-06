package com.example.engmas.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.theme.KufamFont

@Composable
fun QuestionWithAnswerFeedback(
    question: String?,
    options: List<String>,
    correctAnswer: String,
    userAnswer: String? // The selected answer from the user
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Display the question only if it is not null
        question?.let {
            Text(
                text = it,
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        // Display answer options
        options.forEach { option ->
            // Determine the background color and text color based on the selected answer and correct answer
            val backgroundColor = when {
                option == correctAnswer -> Color(0xFF76FB38) // Green for correct answer
                option == userAnswer && userAnswer != correctAnswer -> Color(0xFFF65A5A) // Red for wrong user answer
                else -> Color(0xFFE3F2FD) // Default background
            }

            // Set text color to white for incorrect answers, and default color for the correct one
            val textColor = if (option == userAnswer && option != correctAnswer) Color.White else Color(0xFF757575)

            Button(
                onClick = {}, // No action on click, as it's just to display the answers
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
                    color = textColor, // Apply the determined text color here
                    fontFamily = KufamFont,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionWithAnswerFeedbackPreview() {
    val question = "30. What event does the woman mention?"
    val options = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser", "D. A company picnic")
    val correctAnswer = "C. A fund-raiser"
    val userAnswer = "D. A company picnic" // Example of a wrong answer selected by the user

    // Here, we are testing with the question and all answers present
    QuestionWithAnswerFeedback(
        question = question,
        options = options,
        correctAnswer = correctAnswer,
        userAnswer = userAnswer
    )
}

@Preview(showBackground = true)
@Composable
fun QuestionWithAnswerFeedbackWithoutDPreview() {
    val question = null
    val options = listOf("A. A job fair", "B. A cooking class", "C. A fund-raiser")
    val correctAnswer = "C. A fund-raiser"
    val userAnswer = "B. A cooking class" // Example of a wrong answer selected by the user

    // Here, we are testing with the question but without answer D
    QuestionWithAnswerFeedback(
        question = question,
        options = options,
        correctAnswer = correctAnswer,
        userAnswer = userAnswer
    )
}


