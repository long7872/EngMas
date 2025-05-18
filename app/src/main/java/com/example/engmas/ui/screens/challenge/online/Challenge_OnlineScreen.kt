package com.example.engmas.ui.screens.challenge.online

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.network.SocketManager
import com.example.engmas.ui.navigation.NavigationDestination

object ChallengeOnlineDestination: NavigationDestination {
    override val route = "challenge/online"
    override val titleRes = R.string.tab_challenge_online
}

@Composable
fun Challenge_OnlineScreen(
    onExitButton: () -> Unit,
    viewModel: Challenge_OnlineViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    val matchingState = uiState.matchingState

    val user = uiState.thisUser
    val opponentUser = uiState.opponentUser

    LaunchedEffect(Unit) {
        viewModel.enterMatching()
    }

    when (matchingState) {
        MatchingState.Matching -> {
            Challenge_MatchingScreen(
                userName = user.username,
                userImage = user.photoUrl
            )
        }
        MatchingState.Matched -> {
            Challenge_MatchedScreen(
                userName = user.username,
                userImage = user.photoUrl,
                opponentName = opponentUser.username,
                opponentImage = opponentUser.photoUrl
            )
        }
        MatchingState.Play -> {
            Challenge_PlayOnlineScreen(
                uiState = uiState,
                contentPadding = PaddingValues(0.dp),
                onFinishTimeBar = { viewModel.sendNextQuestion("") },
                onSkipButton = { viewModel.sendNextQuestion("") },
                onSubmitButton = { viewModel.sendNextQuestion(it) },
                onExitButton = {
                    SocketManager.disconnect()
                    onExitButton()
                },
                onPlayAgainButton = { viewModel.rematch() }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun Challenge_OnlineScreenPreview() {
//    Challenge_OnlineScreen()
}