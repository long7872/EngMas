package com.example.engmas.ui.screens.practice.voices.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.screens.practice.voices.data.warnings.Reading
import java.util.Locale

@Composable
fun ReadingTab(
    reading: Reading,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        InfoTab("Total time", formatSecondsToMinutes(reading.totalTime))
        InfoTab("Words read", reading.wordsRead.toString())
        InfoTab("Unique words read", reading.uniqueWordsRead.toString())
        InfoTab("Speed (WCPM)", reading.speedWpmCorrect.toString())
        InfoTab("Completion", "${reading.completion * 100}%")
        InfoTab("Reading time", formatSecondsToMinutes(reading.readingTime))
        InfoTab("Non-reading time", formatSecondsToMinutes(reading.nonReadingTime))
        InfoTab("Words assigned", reading.wordsAssigned.toString())
        InfoTab("Unique words assigned", reading.uniqueWordsAssigned.toString())
        InfoTab("Correct words read", reading.correctWordsRead.toString())
        InfoTab("Words added", reading.wordsAdded.toString())
        InfoTab("Words missed", reading.wordsMissed.toString())
        InfoTab("Words replaced", reading.wordsReplaced.toString())
        InfoTab("Speed wpm", reading.speedWpm.toInt().toString())
        InfoTab("Accuracy", "${reading.accuracy * 100}%")
    }
}

@Composable
private fun InfoTab(
    text: String,
    value: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FBFB), shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFCCE5E5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF199473))
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

fun formatSecondsToMinutes(seconds: Float): String {
    val totalSeconds = seconds.toInt()
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return "${minutes}m ${remainingSeconds}s"
}

@Preview(showBackground = true)
@Composable
private fun ReadingTabPreview() {
    ReadingTab(
        reading = Reading(
            totalTime = 1.54f
        )
    )
}