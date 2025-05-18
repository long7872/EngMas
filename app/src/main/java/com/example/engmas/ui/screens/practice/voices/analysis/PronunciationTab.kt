package com.example.engmas.ui.screens.practice.voices.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.screens.practice.voices.AnalysisScreen
import com.example.engmas.ui.screens.practice.voices.data.VoiceAnalysisResponse
import com.example.engmas.ui.screens.practice.voices.data.fluency.Fluency
import com.example.engmas.ui.screens.practice.voices.data.overall.Overall
import com.example.engmas.ui.screens.practice.voices.data.pronunciation.Phoneme
import com.example.engmas.ui.screens.practice.voices.data.pronunciation.PronouncedWord
import com.example.engmas.ui.screens.practice.voices.data.pronunciation.Pronunciation
import com.example.engmas.ui.screens.practice.voices.data.warnings.Metadata

@Composable
fun PronunciationTab(pronunciation: Pronunciation) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pronunciation.words) { word ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(4.dp)
                        .width(IntrinsicSize.Min)
                ) {
                    Text(
                        text = word.wordText,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2A9D8F)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        word.phonemes.forEach { phoneme ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        shape = RoundedCornerShape(4.dp),
                                        color = when (phoneme.phonemeScore.toInt()) {
                                            in (0..30) -> Color(0xFFF0776D)
                                            in (70..100) -> Color(0xFF599F44)
                                            else -> Color(0xFFF2BF3B)
                                        }
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = phoneme.ipaLabel, color = Color.White)
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        word.phonemes.forEach {
                            Text(
                                text = "${it.phonemeScore.toInt()}%",
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AnalysisScreenPreview() {
    val mockData = Pronunciation(
        words = listOf(
            PronouncedWord("the", listOf(
                Phoneme("ð", 0f), Phoneme("i", 0f))),
            PronouncedWord("dog", listOf(
                Phoneme("d", 0f), Phoneme("ɔ", 0f), Phoneme("g", 0f))),
            PronouncedWord("ran", listOf(
                Phoneme("r", 0f), Phoneme("æ", 0f), Phoneme("n", 0f))),
            PronouncedWord("outside", listOf(
                Phoneme("aʊ", 0f), Phoneme("t", 0f), Phoneme("s", 0f), Phoneme("aɪ", 0f), Phoneme("d", 0f)))
        )
    )

    PronunciationTab(pronunciation = mockData)
}