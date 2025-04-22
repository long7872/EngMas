package com.example.engmas.ui.screens.challenge.online

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.google.firebase.auth.FirebaseAuth

object ChallengeOnlineDestination: NavigationDestination {
    override val route = "challenge/online"
    override val titleRes = R.string.tab_challenge_online
}

@Composable
fun Challenge_OnlineScreen(
    viewModel: Challenge_OnlineViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val auth = FirebaseAuth.getInstance()
    val uiState by viewModel.uiState.collectAsState()
    val userId = auth.currentUser?.uid ?: ""

    LaunchedEffect(userId) {
        viewModel.startMatching(userId)
    }
    Challenge_MatchScreen(
        onSubmitButton = { viewModel.nextQuestion() },
        uiState = uiState
    )
}

@Preview(showBackground = true)
@Composable
private fun Challenge_OnlineScreenPreview() {
    Challenge_OnlineScreen()
}