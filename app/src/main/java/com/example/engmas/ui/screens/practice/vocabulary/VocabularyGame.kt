package com.example.engmas.ui.screens.practice.vocabulary

import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.screens.practice.courses.data.QuestionVocabItem
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.TitleRow

@Composable
fun VocabularyGame(
    title: String,
    item: QuestionVocabItem,
    answerList: List<String>,
    onCorrect: (Boolean) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val mediaPlayer = remember { MediaPlayer() }
    val context = LocalContext.current
    var selectedAnswer by remember { mutableStateOf("") }
    var isAnswered by remember { mutableStateOf(false) }
    val isPlay = item.audio != ""

    LaunchedEffect(Unit) {
        if (isPlay) {
            mediaPlayer.setDataSource(item.audio)
            mediaPlayer.prepare()
            mediaPlayer.setVolume(1.0f, 1.0f)
        }
    }

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
                text = title,
                onClick = onBackClicked
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Text(
                text = "What is the meaning of this word?",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF757575),
                fontSize = 16.sp
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.5f)
            ) {
                WordCard(
                    text = item.word,
                    phonetic = item.phonetic,
                    onAudioClick = {
                        try {
                            if (isPlay) {
                                if (!mediaPlayer.isPlaying) {
                                    mediaPlayer.start()
                                } else {
                                    mediaPlayer.pause()
                                    mediaPlayer.seekTo(0)
                                }
                            } else {
                                Toast.makeText(context, "Sorry, this audio is not available right now", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error playing sound", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

            Selection(
                selectionList = answerList,
                correctAnswer = item.wordVi,
                selectedAnswer = selectedAnswer,
                isAnswered = isAnswered,
                onOptionClicked = { answer ->
                    if (!isAnswered) {
                        selectedAnswer = answer
                        isAnswered = true
                        if (answer == item.wordVi) {
                            onCorrect(true)
                        } else {
                            onCorrect(false)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.padding(12.dp))
        }
    }
}


@Composable
private fun Selection(
    selectionList: List<String>,
    correctAnswer: String,
    selectedAnswer: String,
    isAnswered: Boolean,
    onOptionClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ItemCard(
            cardName = selectionList[0],
            isSelected = selectedAnswer == selectionList[0],
            isCorrect = isAnswered && selectionList[0] == correctAnswer,
            onOptionClicked = onOptionClicked,
            modifier = Modifier.weight(1f))
        ItemCard(
            cardName = selectionList[1],
            isSelected = selectedAnswer == selectionList[1],
            isCorrect = isAnswered && selectionList[1] == correctAnswer,
            onOptionClicked = onOptionClicked,
            modifier = Modifier.weight(1f))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ItemCard(
            cardName = selectionList[2],
            isSelected = selectedAnswer == selectionList[2],
            isCorrect = isAnswered && selectionList[2] == correctAnswer,
            onOptionClicked = onOptionClicked,
            modifier = Modifier.weight(1f))
        ItemCard(
            cardName = selectionList[3],
            isSelected = selectedAnswer == selectionList[3],
            isCorrect = isAnswered && selectionList[3] == correctAnswer,
            onOptionClicked = onOptionClicked,
            modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ItemCard(
    cardName: String,
    isCorrect: Boolean,
    isSelected: Boolean,
    onOptionClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isCorrect -> Color(0xFF76FB38)
        isSelected -> Color(0xFFF65A5A)
        else -> Color.White
    }
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier
            .size(150.dp)
            .padding(4.dp)
            .clickable { onOptionClicked(cardName) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = cardName,
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF757575),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun WordCard(
    text: String,
    phonetic: String,
    onAudioClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            // Tên và phiên âm
            Text(
                text = text,
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color(0xFF4DC5DD),
                textAlign = TextAlign.Center,
            )

            Text(
                text = phonetic,
                fontFamily = KufamFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF4DC5DD),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            IconButton(
                onClick = onAudioClick,
                modifier = Modifier
                    .size(70.dp)
                    .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(50))
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.speaker),
                    contentDescription = "Play sound",
                    tint = Color(0xFF4DC5DD)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VocabularyGamePreview() {
//    Content()
}