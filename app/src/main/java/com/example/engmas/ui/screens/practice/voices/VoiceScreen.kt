@file:JvmName("VoiceScreenKt")

package com.example.engmas.ui.screens.practice.voices

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination

object PracticeVoiceDestination: NavigationDestination {
    override val route = "practice/voice"
    override val titleRes = R.string.tab_voice
}

@Composable
fun VoiceScreen(
    onBackClicked: () -> Unit,
    viewModel: VoiceViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    val result = uiState.analyzedResult

    LaunchedEffect(Unit) {
        viewModel.getSentence()
    }

    val screenState = uiState.screenState

    when (screenState) {
        VoiceScreenState.Record -> {
            RecordScreen(
                expectedText = if (uiState.sentences.isEmpty()) "Loading..."
                    else uiState.sentences[uiState.currentSentence],
                onBackClicked = onBackClicked,
                onCompletedRecord = { file, expectedText ->
                    viewModel.setSentence(expectedText)
                    viewModel.analyzeVoice(file, expectedText, accent = "us")
                },
                onPreviousClicked = { viewModel.previousSentence() },
                onNextClicked = { viewModel.nextSentence() }
            )
        }
        VoiceScreenState.Analysis -> {
            AnalysisScreen(
                expectedText = uiState.selectedSentence,
                result = result,
                onBackClicked = {
                    viewModel.changeScreenState(VoiceScreenState.Record)
                }
            )
        }
    }
}

@Preview
@Composable
private fun VoiceProcessingPreview() {
    VoiceScreen(onBackClicked = {})
}