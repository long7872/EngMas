package com.example.engmas.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont

@Composable
fun QuizHeader(
    currentQuestion: String,
    totalQuestions: String = "200",
    part: String,
    timeLeft: Long // in seconds
) {
    val formattedTime = formatTime(timeLeft)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Question count
        Card(
            border = BorderStroke(1.dp, color = Color(0xFFC4BDBD)),
            shape = MaterialTheme.shapes.extraSmall,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
            modifier = Modifier
                .size(120.dp, 60.dp)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = currentQuestion,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                fontFamily = KufamFont,
                textAlign = TextAlign.Center,
                color = Color(0xFF757575),
                modifier = Modifier.padding(3.dp).fillMaxWidth()
            )
        }

        // Part
        Text(
            text = "Part $part",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2B4EA2),
            fontFamily = KufamFont
        )

        // Timer
        Card(
            border = BorderStroke(1.dp, color = Color(0xFFC4BDBD)),
            shape = MaterialTheme.shapes.extraSmall,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
            modifier = Modifier
                .size(120.dp, 60.dp)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = formattedTime,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                fontFamily = KufamFont,
                textAlign = TextAlign.Center,
                color = Color(0xFF757575),
                modifier = Modifier.padding(3.dp).fillMaxWidth()
            )
        }
    }
}

fun formatTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secondsRemaining = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secondsRemaining)
}

@Preview(showBackground = true)
@Composable
fun QuizHeaderPreview() {
    EngMasTheme {
        QuizHeader(
            currentQuestion = "6",
//            totalQuestions = "200",
            part = "1",
            timeLeft = 7200
        )
    }
}