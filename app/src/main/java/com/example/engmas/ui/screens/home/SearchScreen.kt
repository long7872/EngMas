package com.example.engmas.ui.screens.home

import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.practice.courses.PracticeCourseDestination
import com.example.engmas.ui.theme.KufamFont

object HomeSearchDestination: NavigationDestination {
    override val route = "home/search"
    override val titleRes = R.string.tab_home_search
    const val ITEM_ARGS = "vocab_id"
    val routeWithArgs = "${HomeSearchDestination.route}/{$ITEM_ARGS}"
}

@Composable
fun SearchScreen(
    vocabId: Int,
    viewModel: SearchViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val vocabInfo by viewModel.vocabState.collectAsState()

    val mediaPlayer = remember { MediaPlayer() }

    LaunchedEffect(vocabId) {
        viewModel.collectVocabInfo(vocabId)
    }

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = vocabInfo.word,
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF757575),
            )
            Text(
                text = vocabInfo.wordVi,
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF757575),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Phonetics",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF757575),
            )
            vocabInfo.phonetics.forEach { phonetic ->
                if (phonetic.text != "") {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = phonetic.text,
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF757575),
                            modifier = Modifier.padding(start = 24.dp)
                        )
                        IconButton(
                            onClick = {
                                try {
                                    if (phonetic.audio != "") {
                                        mediaPlayer.reset()
                                        mediaPlayer.setDataSource(phonetic.audio)
                                        mediaPlayer.prepare()
                                        mediaPlayer.setVolume(1.0f, 1.0f)

                                    } else {
                                        Toast.makeText(context, "Sorry, this audio is not available right now", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error playing sound", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(50))
                                .padding(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.speaker),
                                contentDescription = "Play sound",
                                tint = Color(0xFF4DC5DD),
                            )
                        }
                    }
                }
            }
            Text(
                text = "Meanings",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF757575),
            )
            vocabInfo.meanings.forEach { meaning ->
                Text(
                    text = meaning.partOfSpeech.uppercase(),
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(start = 24.dp)
                        .padding(top = 8.dp)
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.fillMaxWidth(2/5f)
                        .padding(horizontal = 8.dp)
                )
                Text(
                    text = "Definitions",
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(start = 12.dp)
                        .padding(vertical = 8.dp)
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                meaning.definitions.forEach { definition ->
                    if (definition.definition != "") {
                        Text(
                            text = "Definition: ${definition.definition}",
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Left,
                            color = Color(0xFF757575),
                            modifier = Modifier.padding(start = 24.dp)
                                .padding(vertical = 8.dp)
                        )
                    }
                    if (definition.definitionVi != "") {
                        Text(
                            text = "Định nghĩa: ${definition.definitionVi}",
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Left,
                            color = Color(0xFF757575),
                            modifier = Modifier.padding(start = 24.dp)
                                .padding(vertical = 8.dp)
                        )
                    }
                    if (definition.example != "") {
                        Text(
                            text = "Example: ${definition.example}",
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Left,
                            color = Color(0xFF757575),
                            modifier = Modifier.padding(start = 24.dp)
                                .padding(vertical = 8.dp)
                        )
                    }
                    HorizontalDivider(
                        thickness = 2.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Text(
                    text = "Synonyms",
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(start = 12.dp)
                        .padding(vertical = 8.dp)
                )
                meaning.synonyms.forEach { item ->
                    Text(
                        text = item,
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Left,
                        color = Color(0xFF757575),
                        modifier = Modifier.padding(start = 24.dp)
                            .padding(vertical = 8.dp)
                    )
                }
                Text(
                    text = "Antonyms",
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(start = 12.dp)
                        .padding(vertical = 8.dp)
                )
                meaning.antonyms.forEach { item ->
                    Text(
                        text = item,
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Left,
                        color = Color(0xFF757575),
                        modifier = Modifier.padding(start = 24.dp)
                            .padding(vertical = 8.dp)
                    )
                }
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        vocabId = 26
    )
}