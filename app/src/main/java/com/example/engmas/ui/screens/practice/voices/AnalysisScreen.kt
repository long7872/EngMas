package com.example.engmas.ui.screens.practice.voices

import android.speech.tts.Voice
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.screens.practice.voices.analysis.ContentRelevanceTab
import com.example.engmas.ui.screens.practice.voices.analysis.FluencyTab
import com.example.engmas.ui.screens.practice.voices.analysis.PronunciationTab
import com.example.engmas.ui.screens.practice.voices.analysis.ReadingTab
import com.example.engmas.ui.screens.practice.voices.data.VoiceAnalysisResponse
import com.example.engmas.ui.screens.practice.voices.data.fluency.Fluency
import com.example.engmas.ui.screens.practice.voices.data.overall.Overall
import com.example.engmas.ui.screens.practice.voices.data.pronunciation.Phoneme
import com.example.engmas.ui.screens.practice.voices.data.pronunciation.PronouncedWord
import com.example.engmas.ui.screens.practice.voices.data.pronunciation.Pronunciation
import com.example.engmas.ui.screens.practice.voices.data.warnings.Metadata
import com.example.engmas.ui.utils.TitleRow


@Composable
fun AnalysisScreen(
    expectedText: String,
    result: VoiceAnalysisResponse,
    onBackClicked: () ->  Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleRow(
                containerColor = Color(0xFFE3F2FD),
                itemColor = Color(0xFF757575),
                text = stringResource(R.string.voice),
                onClick = onBackClicked
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                VoiceScoreVerticalLayout(
                    overallScore = result.overall.englishProficiencyScores.mockIelts.prediction,
                    readingFluency = result.fluency.englishProficiencyScores.mockIelts.prediction,
                    pronunciation = result.pronunciation.englishProficiencyScores.mockIelts.prediction
                )
                FeedbackTabs(
                    expectedText = expectedText,
                    result = result
                )
            }
        }
    }
}

@Composable
private fun VoiceScoreVerticalLayout(
    overallScore: Float,
    readingFluency: Float,
    pronunciation: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Overall Score
        Text("Overall Score", style = MaterialTheme.typography.titleMedium)
        CircularProgressIndicatorWithScore(score = overallScore)

        Spacer(modifier = Modifier.height(32.dp))

        // Skills
        Text("Skills", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(4.dp))

        SkillBar(label = "Reading Fluency", score = readingFluency)
        Spacer(modifier = Modifier.height(8.dp))
        SkillBar(label = "Pronunciation", score = pronunciation)
    }
}

@Composable
fun FeedbackTabs(
    expectedText: String,
    result: VoiceAnalysisResponse
) {
    var selectedTab by remember { mutableStateOf(FeedbackTab.ContentRelevance) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Tabs
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = Color.White,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            FeedbackTab.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab.ordinal == index,
                    onClick = { selectedTab = tab },
                    text = {
                        Text(
                            text = tab.label,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTab.ordinal == index) Color(0xFF2BBBAD) else Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth() // hoặc dùng weight nếu có sibling
                                .basicMarquee(
                                    iterations = Int.MAX_VALUE,
                                    repeatDelayMillis = 3000,
                                    initialDelayMillis = 0,
                                    velocity = 30.dp
                                )
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nội dung hiển thị theo từng tab
        when (selectedTab) {
            FeedbackTab.ContentRelevance -> ContentRelevanceTab(
                expectedText = expectedText,
                feedbackText = result.metadata.predictedText
            )
            FeedbackTab.Pronunciation -> PronunciationTab(
                pronunciation = result.pronunciation
            )
            FeedbackTab.Fluency -> FluencyTab(
                fluency = result.fluency
            )
            FeedbackTab.Reading -> ReadingTab(
                reading = result.reading
            )
        }
    }
}

@Composable
private fun CircularProgressIndicatorWithScore(score: Float) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = { score / 9f },
            modifier = Modifier.size(120.dp),
            color = Color(0xFF4DB6AC), // màu xanh như hình
            strokeWidth = 10.dp,
            trackColor = Color.LightGray,
            gapSize = (-10).dp
        )
        Text(text = "%.1f".format(score), style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
private fun SkillBar(label: String, score: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
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
                    .fillMaxWidth(score / 9f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF4DB6AC))
            )
        }
        Box{
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "0",
                    color = Color(0xFF757575),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = "9",
                    color = Color(0xFF757575),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            if (score != 0.0f) {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .fillMaxWidth(score / 9f)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "%.1f".format(score),
                        color = Color(0xFF4DB6AC),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnalysisScreenPreview() {
    AnalysisScreen(
        expectedText = "Hello, how are you?",
        onBackClicked = {},
        result = VoiceAnalysisResponse(
            overall = Overall(
                overallScore = 4.3f
            ),
            fluency = Fluency(
                overallScore = 6.4f
            ),
            pronunciation = Pronunciation(
                words = listOf(
                    PronouncedWord("the", listOf(
                        Phoneme("ð", 76f), Phoneme("i", 23f))),
                    PronouncedWord("dog", listOf(
                        Phoneme("d", 97f), Phoneme("ɔ", 23f), Phoneme("g", 0f))),
                    PronouncedWord("ran", listOf(
                        Phoneme("r", 12f), Phoneme("æ", 56f), Phoneme("n", 0f))),
                    PronouncedWord("outside", listOf(
                        Phoneme("aʊ", 0f), Phoneme("t", 0f), Phoneme("s", 0f), Phoneme("aɪ", 0f), Phoneme("d", 0f)))
                ),
                expectedText = "Hello, how are you?"
            ),
            metadata = Metadata(
                predictedText = "Hello, Hello?"
            )
        )
    )
}