package com.example.engmas.ui.screens.practice.voices.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.screens.practice.voices.data.fluency.FeedbackDetail
import com.example.engmas.ui.screens.practice.voices.data.fluency.Fluency
import com.example.engmas.ui.screens.practice.voices.data.fluency.FluencyFeedback
import com.example.engmas.ui.screens.practice.voices.data.fluency.FluencyMetrics

@Composable
fun FluencyTab(
    fluency: Fluency,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TranscriptSection(fluency.feedback.taggedTranscript)
        SpeechRateSection(fluency)
        MetricBox("Number of pauses", fluency.metrics.pauses, fluency.feedback.pauses.feedbackText)
        MetricBox("Number of filler words", fluency.metrics.fillerWords, fluency.feedback.fillerWords.feedbackText)
        MetricBox("Filler words per minute", fluency.metrics.fillerWordsPerMin.toInt(), "", false)
    }
}

@Composable
fun TranscriptSection(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FBFB), shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFCCE5E5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column {
            Text("Detailed transcript", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF199473))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Feedback", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(text, fontSize = 14.sp)
        }
    }
}

@Composable
fun SpeechRateSection(fluency: Fluency) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FBFB), shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFCCE5E5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column {
            Text("Speech rate", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF199473))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Feedback", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(fluency.feedback.speechRate.feedbackText, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(8.dp))

            // Simulated bar and label (replace with actual graph later)
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(18.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(fluency.metrics.speechRate / 200f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF4DB6AC))
                    )
                }
                Box {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Slow",
                            color = Color(0xFF757575),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "Fast",
                            color = Color(0xFF757575),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetricBox(title: String, value: Int, feedback: String, haveFeedback: Boolean = true) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FBFB), shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFCCE5E5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF199473))
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (haveFeedback) "Feedback" else "", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(value.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            if (feedback.isNotBlank()) {
                Text(feedback, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FluencyTabPreview() {
    val sampleFluency = Fluency(
        overallScore = 85.0f,
        metrics = FluencyMetrics(
            speechRate = 176,
            speechRateOverTime = listOf(170, 176, 180),
            pauses = 0,
            fillerWords = 0,
            fillerWordsPerMin = 0.0f,
            discourseMarkers = listOf(),
            pauseDetails = listOf(),
            repetitions = listOf(),
            fillerWordsDetails = listOf()
        ),
        feedback = FluencyFeedback(
            speechRate = FeedbackDetail(
                feedbackText = "You are speaking at a normal pace."
            ),
            pauses = FeedbackDetail(
                feedbackText = "You are speaking without making any long pauses."
            ),
            fillerWords = FeedbackDetail(
                feedbackText = "You are speaking without using any filler words."
            ),
            taggedTranscript = "What's our friend outside?"
        )
    )
    FluencyTab(fluency = sampleFluency)
}