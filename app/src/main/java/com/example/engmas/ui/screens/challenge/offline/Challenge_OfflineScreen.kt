package com.example.engmas.ui.screens.challenge.offline

import android.app.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination

object ChallengeOfflineDestination: NavigationDestination {
    override val route = "challenge/offline"
    override val titleRes = R.string.tab_challenge_offline
}

@Composable
fun Challenge_OfflineScreen(
    onExit: () -> Unit,
    viewModel: Challenge_OfflineViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val screenState = uiState.screenState

    when (screenState) {
        OfflineScreenState.Play -> {
            Challenge_PlayOfflineScreen(
                uiState = uiState,
                onFinishTimeBar = { viewModel.nextQuestion("") },
                onSkipButton = { viewModel.nextQuestion("") },
                onSubmitButton = { viewModel.nextQuestion(it) }
            )
        }
        OfflineScreenState.Result -> {
            Challenge_OfflineResult(
                userImage = uiState.thisUser.photoUrl,
                questionCorrected = uiState.thisUserScore,
                onExit = onExit,
                onPlayAgain = { viewModel.playAgain() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun Challenge_OfflineScreenPreview() {
//    Challenge_OfflineScreen()
}