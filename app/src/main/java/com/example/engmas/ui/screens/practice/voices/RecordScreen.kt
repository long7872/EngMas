package com.example.engmas.ui.screens.practice.voices

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.RequestAudioPermission
import com.example.engmas.audio.WavRecorder
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.Previous_Next_Button
import com.example.engmas.ui.utils.TitleRow
import java.io.File

@Composable
fun RecordScreen(
    expectedText: String,
    onBackClicked: () -> Unit,
    onCompletedRecord: (File, String) -> Unit,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }
    val outputFile = remember {
        File(context.cacheDir, "recording.wav")
    }

    if (isRecording) {
        RequestAudioPermission {
            WavRecorder.startRecording(outputFile)
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
                text = stringResource(R.string.voice),
                onClick = onBackClicked
            )
            Column {
                Column {
                    Text(
                        text = "Tap the micro then read the text below",
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
                    )
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                        modifier = modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(dimensionResource(R.dimen.padding_medium)),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = expectedText,
                                fontFamily = KufamFont,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF757575),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
                            )
                        }
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                        .fillMaxSize()
                ) {
                    IconButton(
                        onClick = {
                            if (!isRecording) {
                                isRecording = true // sẽ gọi RequestAudioPermission
                            } else {
                                WavRecorder.stopRecording()
                                onCompletedRecord(outputFile, expectedText)
                                isRecording = false
                            }
                        },
                        modifier = Modifier.size(90.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                if (isRecording) R.drawable.stop_record
                                else R.drawable.record
                            ),
                            contentDescription = stringResource(R.string.record),
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
                Previous_Next_Button(
                    onPreviousClicked = onPreviousClicked,
                    onNextClicked = onNextClicked
                )
            }
        }
    }
}

@Preview
@Composable
private fun VoiceScreenPreview() {
    RecordScreen(
        expectedText = "My family has 5 members",
        onBackClicked = {},
        onCompletedRecord = { _,_ -> },
        onPreviousClicked = {},
        onNextClicked = {}
    )
}