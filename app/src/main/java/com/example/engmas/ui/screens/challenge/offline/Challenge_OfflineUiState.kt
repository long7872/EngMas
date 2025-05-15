package com.example.engmas.ui.screens.challenge.offline

import com.example.engmas.data.model.User
import com.example.engmas.ui.screens.challenge.online.MatchingState
import com.example.engmas.ui.screens.challenge.online.ResultState

data class Challenge_OfflineUiState(
    val screenState: OfflineScreenState = OfflineScreenState.Play,
    val thisUser: User = User(),
    val originalList: List<String> = emptyList(),
    val scrambledList: List<String> = emptyList(),
    val thisUserCurrentQuestion: Int = 0,
    val thisUserScore: Int = 0,
)

enum class OfflineScreenState {
    Play,
    Result
}
