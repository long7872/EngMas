package com.example.engmas.ui.screens.practice.voices.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ContentRelevanceTab(
    expectedText: String,
    feedbackText: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF8FBFB), shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFCCE5E5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Expected Text",
                color = Color(0xFF0C4B5F),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = expectedText,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Transcript - What Was Said",
                color = Color(0xFF0C4B5F),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Feedback",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feedbackText,
                color = Color(0xFFFFC107),
//                textDecoration = TextDecoration.LineThrough
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ContentRelevancePreview() {
    ContentRelevanceTab(
        expectedText = "dawdaw",
        feedbackText = "adawdadw"
    )
}