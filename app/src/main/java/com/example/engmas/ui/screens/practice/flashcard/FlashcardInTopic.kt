package com.example.engmas.ui.screens.practice.flashcard

import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.data.model.Topic
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.practice.courses.data.QuestionStatus
import com.example.engmas.ui.screens.practice.courses.data.QuestionVocabItem
import com.example.engmas.ui.screens.practice.vocabulary.VocabularyViewModel
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.TitleRow

object PracticeFlashCardInTopicDestination: NavigationDestination {
    override val route = "practice/flashcard/learning"
    override val titleRes = R.string.tab_flashcard_learning
    const val ITEM_ARGS = "topicId"
    val routeWithArgs = "$route/{$ITEM_ARGS}"
}

@Composable
fun FlashcardInTopic(
    topicId: Int,
    onBackClicked: () -> Unit,
    viewModel: VocabularyViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(topicId) {
        viewModel.getVocabsInTopic(topicId)
    }
    val selectedTopic = uiState.selectedTopic
    val vocabs = uiState.listVocabsInTopic

    LaunchedEffect(selectedTopic) {
        viewModel.setSelectedVocab(vocabs.firstOrNull() ?: QuestionVocabItem())
    }

    val selectedItem = uiState.selectedVocab
    val isDone = uiState.isDone
    Log.d("Flashcard UI", "isDone status: $isDone")

    FlashcardInTopicScreen(
        selectedTopic = selectedTopic,
        item = selectedItem,
        onMarkReviewClicked = {
            if (!isDone) {
                viewModel.updateStatusForVocabItem(selectedItem, QuestionStatus.Review)
                viewModel.nextFlashcard()
            } else {
                onBackClicked()
            }
        },
        onMarkKnownClicked = {
            if (!isDone) {
                viewModel.updateStatusForVocabItem(selectedItem, QuestionStatus.Known)
                viewModel.nextFlashcard()
            } else {
                onBackClicked()
            }
        },
        onBackClicked = onBackClicked
    )
}

@Composable
fun FlashcardInTopicScreen(
    selectedTopic: Topic,
    item: QuestionVocabItem,
    onMarkReviewClicked: () -> Unit,
    onMarkKnownClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val mediaPlayer = remember { MediaPlayer() }
    val context = LocalContext.current
    val isPlay = item.audio != ""

    LaunchedEffect(item) {
        if (isPlay) {
            mediaPlayer.reset()
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
                text = "${selectedTopic.topicName} - ${selectedTopic.topicNameVi}",
                onClick = onBackClicked
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.8f)
            ) {
                Flashcard(
                    text = item.word,
                    phonetic = item.phonetic,
                    meaning = item.wordVi,
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
                    },
                )
            }

            OutlinedButton(
                onClick = onMarkReviewClicked,
                border = BorderStroke(1.dp, Color(0xFFD3D3D3)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(R.dimen.padding_smaller_medium),
                        start = dimensionResource(R.dimen.padding_smaller_medium),
                        end = dimensionResource(R.dimen.padding_smaller_medium),
                        bottom = 2.dp
                    )
            ) {
                Text(
                    text = "Mark for review",
                    color = Color(0xFF757575),
                    fontFamily = KufamFont,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Button(
                elevation = ButtonDefaults.buttonElevation(4.dp),
                onClick = onMarkKnownClicked,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_smaller_medium),
                        end = dimensionResource(R.dimen.padding_smaller_medium)
                    )
            ) {
                Text(
                    text = "Mark as known",
                    color = Color(0xFFFFFFFF),
                    fontFamily = KufamFont,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
private fun Flashcard(
    text: String,
    phonetic: String,
    meaning: String,
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
            // Hình ảnh
//            Image(
//                painter = painterResource(id = iconRes),
//                contentDescription = stringResource(iconDes),
//                modifier = Modifier
//                    .size(250.dp) // Điều chỉnh kích thước của hình ảnh
//                    .clip(RoundedCornerShape(15.dp)) // Bo tròn cho hình ảnh
//            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tên và phiên âm
            Text(
                text = "$text",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
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

            Text(
                text = meaning,
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
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CoursesPreview() {
//    Content()
}