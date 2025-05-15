package com.example.engmas.ui.screens.challenge.online

import com.example.engmas.data.model.User

data class Challenge_OnlineUiState(
    val matchingState: MatchingState = MatchingState.Matching,
    val resultState: ResultState = ResultState.None,
    val thisUser: User = User(),
    val opponentUser: User = User(),
    val originalList: List<String> = emptyList(),
    val scrambledList: List<String> = emptyList(),
    val thisUserCurrentQuestion: Int = 0,
    val opponentUserCurrentQuestion: Int = 0,
    val thisUserScore: Int = 0,
    val opponentUserScore: Int = 0,
    val isGameStarted: Boolean = false,
)

enum class MatchingState {
    Matching,    // In the process of matching
    Matched,      // Successfully matched
    Play,
}
enum class ResultState {
    None,
    Win,    // In the process of matching
    Lose      // Successfully matched
}