package com.example.engmas.ui.screens.challenge.online

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination

object ChallengeOnlineDestination: NavigationDestination {
    override val route = "challenge/online"
    override val titleRes = R.string.tab_challenge_online
}

@Composable
fun Challenge_OnlineScreen(
    viewModel: Challenge_OnlineViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Challenge_MatchScreen(uiState = uiState)
}

@Preview(showBackground = true)
@Composable
private fun Challenge_OnlineScreenPreview() {
    Challenge_OnlineScreen()
}